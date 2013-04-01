package com.byfdevelopment.popcine;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.Service;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.byfdevelopment.popcine.database.PopCineDataSource;
import com.byfdevelopment.popcine.objects.Movie;
import com.byfdevelopment.popcine.objects.PopCine;
import com.byfdevelopment.popcine.objects.Theater;

/**
 * 
 * Task responsável por pesquisar na internet os cinemas, filmes e horários da
 * cidade.
 * 
 * @author bruno
 */

public class ScratchingTask extends AsyncTask<Void, Void, Void> {

	private String dublado = "D\nu\nb\nl\na\nd\no";
	private String legendado = "L\ne\ng\ne\nn\nd\na\nd\no";
	private String original = "O\nr\ni\ng\ni\nn\na\nl";

	/* Lista de Cinemas encontradas */
	private List<Theater> list;
	/*
	 * Lista de filmes independentes do cinema (está repetindo filmes, eu ainda
	 * não arrumei isso)
	 */
	private List<Movie> listMovies;

	Context context;
	PopCineDataSource database;
	OnScratchingTaskListener listener;

	public ScratchingTask(Context context, OnScratchingTaskListener taskListener) {
		super();
		this.context = context;
		this.listener = taskListener;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		list = new ArrayList<Theater>();
		listMovies = new ArrayList<Movie>();
		database = new PopCineDataSource(context);

		if (listener != null)
			listener.onPreExecute();
	}

	@Override
	protected Void doInBackground(Void... params) {

		/*
		 * ConnectivityManager connectivity = (ConnectivityManager)
		 * context.getSystemService(Service.CONNECTIVITY_SERVICE); if
		 * (!connectivity.getActiveNetworkInfo().isConnected()) return null;
		 */

		try {
			Log.w("Testando uri parser", "http://www.google.com/movies?hl=pt-br&near=" + Uri.encode(PopCine.city).toString());
			Document doc = Jsoup.connect("http://www.google.com/movies?hl=pt-br&near=" + Uri.encode(PopCine.city).toString()).get();

			/* Busca os cinemas da cidade */
			Elements theaters = doc.getElementsByClass("theater");

			/* Para cada cinema faça */
			for (Element t : theaters) {

				/* Cada cinema tem uma descrição e os seus filmes */
				/* Primeiro pega a descrição */
				Element desc = t.getElementsByClass("desc").get(0);
				/* A descrição possui o nome e informaçoes gerais */
				String name;
				Element theaterName = desc.getElementsByClass("name").get(0);
				if (!theaterName.getElementsByTag("a").isEmpty()) {
					name = theaterName.getElementsByTag("a").get(0).text();
				} else {
					name = theaterName.text();
				}
				String info = desc.getElementsByClass("info").get(0).text();
				/* Cria o cinema */
				Theater theater = new Theater(name, info);

				/* Agora pega a lista de filmes do cinema */
				Elements movies = t.getElementsByClass("movie");
				/* Para cada filme faça */
				for (Element m : movies) {
					/*
					 * Cada filme possui o nome, informaçoes gerais, e seus
					 * horários disponíveis
					 */
					String mname = m.getElementsByClass("name").get(0).text();
					String minfo = m.getElementsByClass("info").get(0).text();
					/* Cria o filme */
					Movie movie = new Movie(mname, minfo);

					/*
					 * Quando voce pega os elementos pela classe "times", ele
					 * retorna apenas um elemento pois os horarios estão todos
					 * dentro, por isso é necessário apartir desse elemento
					 * pegar os filhos com tag "span". Agora virá filhos que não
					 * são horarios, são apenas cores sem conteúdos, devemos
					 * tratar isto.
					 */
					Elements times = m.getElementsByClass("times");
					times = times.get(0).getElementsByTag("span");
					for (Element time : times) {
						String timeText = time.text();
						/*
						 * Dá uma limpada marota nos caracteres lixos, isto é
						 * necessário.
						 */
						timeText = timeText.replaceAll("[^0-9:]", "");

						/* Agora vamos pegar o link do ingresso */
						Elements hrefTag = time.getElementsByTag("a");
						String ticket = null;
						if (hrefTag != null && hrefTag.size() > 0) {
							ticket = hrefTag.get(0).attr("href");
						}
						movie.add(timeText, null);
					}

					listMovies.add(movie);
					theater.add(movie);
				}
				list.add(theater);
			}
		} catch (IOException e2) {
			Log.w("Jsoup Connection", "Error in connecting to Google Movies");
		}
		return null;
	}


	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);

		if (listener != null)
			listener.onPosExecute();

		new PosterTask().execute();
	}

	public interface OnScratchingTaskListener {
		public void onPreExecute();

		public void onPosExecute();
	}

	class PosterTask extends AsyncTask<Object, Void, Void> {

		@Override
		protected Void doInBackground(Object... arg0) {
			/**
			 * Atualização dos posteres dos filmes
			 */

			Document moviesPostersList;
			Document moviesPostersList2;

			/* Tem duas páginas de filmes */

			String url = "http://filmow.com/filmes/nos-cinemas/";
			String url2 = "http://filmow.com/filmes/nos-cinemas/?pagina=2";
			try {
				// Conecta e separa apenas a lista de filmes
				moviesPostersList = Jsoup.connect(url).get();
				moviesPostersList2 = Jsoup.connect(url2).get();
				Elements posters = moviesPostersList.getElementsByClass("movie_list_item");
				Elements posters2 = moviesPostersList2.getElementsByClass("movie_list_item");

				int i = 0;
				// Para cada filme na lista (devemos atualizar o cartaz)
				for (Movie movie : listMovies) {

					String address = null; // por enquanto nao temos o url da
											// imagem do poster
					Element lessDistanceElement = posters.first(); // Assumimos
																	// que o
																	// filme por
																	// enquanto
																	// é o
																	// primeiro
																	// elemento
																	// do site

					// para cada filme da lista do site!!
					for (Element ele : posters) {
						// Obtemos o nome do filme que ta na lista do site
						String x = ele.getElementsByTag("strong").first().text();

						// Se esse nome for mais parecido com o nome do filme
						// que a gente tem que o elemento atual
						if (LevenshteinDistance.computeLevenshteinDistance(x, movie.name) < LevenshteinDistance.computeLevenshteinDistance(
								lessDistanceElement.getElementsByTag("strong").first().text(), movie.name)) {
							// Esse filme vai ser escolhido para ser o
							// correspondente do nosso filme no site
							lessDistanceElement = ele;
						}
					}

					// Fazemos a mesma coisa para a segunda página
					for (Element ele : posters2) {
						String x = ele.getElementsByTag("strong").first().text();

						if (LevenshteinDistance.computeLevenshteinDistance(x, movie.name) < LevenshteinDistance.computeLevenshteinDistance(
								lessDistanceElement.getElementsByTag("strong").first().text(), movie.name)) {
							lessDistanceElement = ele;
						}
					}

					// Depois de achar o correspondente do nosso filme no site,
					// pegue o endereço da imagem
					address = lessDistanceElement.getElementsByTag("img").first().attr("src");

					// O site filmow tem um padrão no nome dos arquivos de
					// poster, em que você pode setar outra resolução.
					// Pego a maior resolução que eu achei.
					// address = address.replaceAll("([0-9]+)x([0-9]+)_crop",
					// "290x478");

					// Baixe o drawable do filme.
					new PosterDownloadTask().execute(address, i, movie);
					i++;

				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

	}

	/**
	 * task de download dos posters (permite que varios posteres sejam baixados
	 * ao mesmo tempo diminuindo assim o tempo para o usuario ver o poster)
	 * 
	 * @author yuriclaure
	 * 
	 */
	class PosterDownloadTask extends AsyncTask<Object, Void, Void> {

		Movie posterMovie;

		@Override
		protected Void doInBackground(Object... arg0) {

			String address = (String) arg0[0];
			int i = (Integer) arg0[1];
			Movie movie = (Movie) arg0[2];
			posterMovie = movie;

			URL image;
			try {

				image = new URL(address);

				InputStream content = (InputStream) image.getContent();
				movie.poster = Drawable.createFromStream(content, "src");

				HashMap<String, Object> item = new HashMap<String, Object>();
				item.put("imagem", movie.poster);
				item.put("name", movie.name);
				item.put("show_times", movie.getShowTimesAsString());
				item.put("tipo", (movie.type == 0) ? dublado : (movie.type == 1) ? legendado : original);
				item.put("is3d", movie.is3D ? "3d" : "no");

			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {

			PopCineDataSource database = new PopCineDataSource(context);
			database.open();
			database.insertMovie(posterMovie);
			database.close();
			super.onPostExecute(result);
		}

	}

}

/**
 * Classe que descobre a distancia entre duas strings Calcula quantos caracteres
 * (no minimo) precisarão ser alterados/removidos/adicionados para a primeira
 * string ficar igual a segunda string.
 * 
 */
class LevenshteinDistance {
	private static int minimum(int a, int b, int c) {
		return Math.min(Math.min(a, b), c);
	}

	public static int computeLevenshteinDistance(CharSequence str1, CharSequence str2) {
		int[][] distance = new int[str1.length() + 1][str2.length() + 1];

		for (int i = 0; i <= str1.length(); i++)
			distance[i][0] = i;
		for (int j = 1; j <= str2.length(); j++)
			distance[0][j] = j;

		for (int i = 1; i <= str1.length(); i++)
			for (int j = 1; j <= str2.length(); j++)
				distance[i][j] = minimum(distance[i - 1][j] + 1, distance[i][j - 1] + 1, distance[i - 1][j - 1] + ((str1.charAt(i - 1) == str2.charAt(j - 1)) ? 0 : 1));

		return distance[str1.length()][str2.length()];
	}
}
