import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashSet;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ScrapeNowAllTheThings {

	private static final int TIMEOUT = 10*1000; //ms, 10 sec
	private static final String BROWSER = "Mozilla";
	private static final int REQUEST_WAIT = (int) (1000/3.5);
	private static final String URL = "https://genius.com/albums/Greydon-square/";
	private static final String[] ALBUMS = 
		{"The-compton-effect", 
		"Type-ii-the-mandelbrot-set",
		"Type-i-the-kardashev-scale",
		"Omniverse-type-3-aum-niverse",
		"The-cpt-theorem"};
	private static final int WORD_MAX = 35000;
	
	//just been running main and looking at the output, cause bad
	
	/* It's dangerous w/o documentation. Here, take this:
	 * 		https://jsoup.org/apidocs/org/jsoup/
	 * for selecting stuff from Elements
	 * 		https://jsoup.org/apidocs/org/jsoup/select/Selector.html
	 * 
	 * TODO:
	 * Use genius api- otherwise have to filter each of genius' hover references and random <br>
	 * For clean, get rid of [intro] type things
	 * 		probably an easy way to handle <br> and <hrefs> w/jsoup if not using genuis API
	 * 		Handle Greydon's use of quotes? -meh
	 * 		split on white space, toss punctuation
	 * 		to lower to avoid "test" and "Test" both getting hashed
	 * hash set. if not contained, add.
	 * 		word count consideration? something like 35000 words only?
	 * 			order of albums if all albums.wordCount > MAX_WORDS?
	 */
	
	public static void main(String[] args)
	{
		Scrape(URL + ALBUMS[0]);
		
//		for(String s: ALBUMS)
//		{
//			scrape(URL + s);
//		}
	}
	
	public static void Scrape(String url)
	{
		Document doc;
		Elements elems;
		FileOutputStream out;
		Elements seriesElem;
		String nextUrl;
		String[] words;
		HashSet<String> hash;
		int wordCount = 0;
		try
		{
			doc = Jsoup.connect(url).timeout(TIMEOUT).userAgent(BROWSER).get();
			elems = doc.select(".song_link");
			System.out.println(elems.size());
			hash = new HashSet();
			for(Element e: elems)
			{
				System.out.println(e.attr("abs:href"));
				MyWait();
				doc = Jsoup.connect(e.attr("abs:href")).timeout(TIMEOUT).userAgent(BROWSER).get();
				seriesElem = doc.select(".lyrics p");
				System.out.println(seriesElem.size());
				for(Element lyric: seriesElem)
				{
					System.out.println(lyric);
					//^^easy way to filter out html junk
//					words = Clean(lyric);
//					for(String word: words)
//					{
//						//check on wordCount == WORD_MAX and break? bad practice but yolo
//						if(!hash.contains(word))
//						{
//							hash.add(word);
//							wordCount++;
//						}
//					}
				}
			}
		}catch (IOException e) {
			e.printStackTrace();
			System.out.println("\nDead. Check your internet connection and the site passed. "
				+ "Timeout is currently: " + TIMEOUT + " ms");
		}
		MyWait();
	}
	
	private static String[] Clean(Element lyric) {
		// TODO Auto-generated method stub
		return null;
	}

	public static void MyWait()
	{
		long startTime = 0L;
		long elapsedTime = 0L;
		startTime = System.currentTimeMillis();
		while (elapsedTime < REQUEST_WAIT) {
		    //perform db poll/check
		    elapsedTime = (new Date()).getTime() - startTime;
		}
	}
}
