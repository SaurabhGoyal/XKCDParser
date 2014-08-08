import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class XKCDParser {

	public static void fetchImages(int startingPost, int endingPost,String outputLocation) {

		for (int postNum = startingPost; postNum <= endingPost; postNum++) {

			// System.out.print("getting post " + postNum + " - ");
			Document doc;
			try {
				doc = Jsoup.connect("http://xkcd.com/" + postNum + "/")
						.timeout(0).get();

				Element division = doc.getElementById("comic");
				Elements image = division.getElementsByTag("img");
				String imageSource = image.attr("src");
				String imageDestination = outputLocation
						+ postNum
						+ "_"
						+ imageSource
								.substring(imageSource.lastIndexOf('/') + 1);

				BufferedImage img = null;
				try {
					URL url = new URL(imageSource);
					img = ImageIO.read(url);
					ImageIO.write(img, "png", new File(imageDestination));

				} catch (IOException e) {
					System.err.println("Couldn't fetch the image");
				}
				// System.out.println("completed");

			} catch (IOException e) {
				System.err.println("Couldn't fetch the post");
			}
		}
	}

	public static void main(String[] args) {
		fetchImages(1382, 1390, "d:\\xkcd\\");
	}
}
