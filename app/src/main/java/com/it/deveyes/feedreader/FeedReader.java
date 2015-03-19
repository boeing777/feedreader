package com.it.deveyes.feedreader;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import android.util.Xml;
import com.it.deveyes.feedreader.models.Channel;
import com.it.deveyes.feedreader.models.ChannelImage;
import com.it.deveyes.feedreader.models.Item;

public class FeedReader {

	public List<Channel> parse(InputStream in) throws XmlPullParserException, IOException {
		XmlPullParser parser = Xml.newPullParser();
		parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
		parser.setInput(in, null);
		parser.nextTag();
		return readChannels(parser);

	}

	private List<Channel> readChannels(XmlPullParser parser) throws IOException, XmlPullParserException {
		ArrayList<Channel> channels = new ArrayList<Channel>();

		parser.require(XmlPullParser.START_TAG, null, "rss");
		while (parser.next() != XmlPullParser.END_TAG) {
			if (parser.getEventType() != XmlPullParser.START_TAG) {
				continue;
			}
			String name = parser.getName();
			if (name.equals("channel")) {
				channels.add(readChannel(parser));

			} else {
				skip(parser);
			}
		}
		return channels;
	}

	private Channel readChannel(XmlPullParser parser) throws IOException, XmlPullParserException {
        Channel c = new Channel();
		ArrayList<Item> items = new ArrayList<Item>();
		parser.require(XmlPullParser.START_TAG, null, "channel");
		while (parser.next() != XmlPullParser.END_TAG) {
			if (parser.getEventType() != XmlPullParser.START_TAG) {
				continue;
			}
			String name = parser.getName();
			if (name.equals("title")) {
				parser.require(XmlPullParser.START_TAG, null, "title");
				c.setTitle(readText(parser));
				parser.require(XmlPullParser.END_TAG, null, "title");

			} else if (name.equals("link")) {
				parser.require(XmlPullParser.START_TAG, null, "link");
				c.setLink(readText(parser));
				parser.require(XmlPullParser.END_TAG, null, "link");

			} else if (name.equals("description")) {
				parser.require(XmlPullParser.START_TAG, null, "description");
				c.setDescription(readText(parser));
				parser.require(XmlPullParser.END_TAG, null, "description");
			} else if (name.equals("item")) {
				items.add(readItem(parser));
			} else if (name.equals("image")) {
				c.setImage(readChannelImage(parser));
			} else {
				skip(parser);
			}

		}
        c.setItems(items);
		return c;
	}


    private ChannelImage readChannelImage(XmlPullParser parser) throws IOException, XmlPullParserException {
        ChannelImage image = new ChannelImage();
        parser.require(XmlPullParser.START_TAG, null, "image");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("title")) {
                image.setTitle(validateString("title", parser));
            } else if (name.equals("url")) {
                image.setUrl(validateString("url", parser));
            } else if (name.equals("link")) {
                image.setLink(validateString("link", parser));
            } else {
                skip(parser);
            }
        }
        return image;

    }


    private Item readItem(XmlPullParser parser) throws IOException, XmlPullParserException {
        Item i = new Item();
        parser.require(XmlPullParser.START_TAG, null, "item");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("title")) {
                i.setTitle(validateString("title", parser));
            } else if (name.equals("link")) {
                i.setLink(validateString("link", parser));
            } else if (name.equals("enclosure")) {
                i.setEnclosure(validateString("enclosure", parser));
            } else if (name.equals("description")) {
                i.setDescription(validateString("description", parser));
            } else if (name.equals("pubDate")) {
                i.setPubDate(validateString("pubDate", parser));
            } else {
                skip(parser);
            }
        }
        return i;

    }

    private void skip(XmlPullParser parser) throws IOException, XmlPullParserException {
        int depth = 1;
        while (depth != 0) {
            int c = parser.next();
            if (c == XmlPullParser.START_TAG)
                depth++;
            else if (c == XmlPullParser.END_TAG)
                depth--;
        }
    }

    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }else{
            String tag = parser.getName();
            if (tag.equals("enclosure")) {
                result = parser.getAttributeValue(null, "url");
            }
        }

        return result;
    }

    private String validateString(String s, XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, null, s);
        String result = readText(parser);
        parser.require(XmlPullParser.END_TAG, null, s);
        return result;

    }

}
