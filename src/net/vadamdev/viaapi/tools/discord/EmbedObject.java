package net.vadamdev.viaapi.tools.discord;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ?, edited by VadamDev
 */
public class EmbedObject {
    private String title, description, url;
    private Color color;

    private Footer footer;
    private Thumbnail thumbnail;
    private Image image;
    private final java.util.List<Field> fields = new ArrayList<>();

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public Color getColor() {
        return color;
    }

    public Footer getFooter() {
        return footer;
    }

    public Thumbnail getThumbnail() {
        return thumbnail;
    }

    public Image getImage() {
        return image;
    }

    public List<Field> getFields() {
        return fields;
    }

    public EmbedObject setTitle(String title) {
        this.title = title;
        return this;
    }

    public EmbedObject setDescription(String description) {
        this.description = description;
        return this;
    }

    public EmbedObject setUrl(String url) {
        this.url = url;
        return this;
    }

    public EmbedObject setColor(Color color) {
        this.color = color;
        return this;
    }

    public EmbedObject setFooter(String text, String icon) {
        this.footer = new Footer(text, icon);
        return this;
    }

    public EmbedObject setThumbnail(String url) {
        this.thumbnail = new Thumbnail(url);
        return this;
    }

    public EmbedObject setImage(String url) {
        this.image = new Image(url);
        return this;
    }

    public EmbedObject addField(String name, String value, boolean inline) {
        this.fields.add(new Field(name, value, inline));
        return this;
    }

    public class Footer {
        private final String text;
        private final String iconUrl;

        public Footer(String text, String iconUrl) {
            this.text = text;
            this.iconUrl = iconUrl;
        }

        public String getText() {
            return text;
        }

        public String getIconUrl() {
            return iconUrl;
        }
    }

    public class Thumbnail {
        private final String url;

        public Thumbnail(String url) {
            this.url = url;
        }

        public String getUrl() {
            return url;
        }
    }

    public class Image {
        private final String url;

        public Image(String url) {
            this.url = url;
        }

        public String getUrl() {
            return url;
        }
    }

    public class Field {
        private final String name;
        private final String value;
        private final boolean inline;

        public Field(String name, String value, boolean inline) {
            this.name = name;
            this.value = value;
            this.inline = inline;
        }

        public String getName() {
            return name;
        }

        public String getValue() {
            return value;
        }

        public boolean isInline() {
            return inline;
        }
    }
}
