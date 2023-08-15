package net.vadamdev.viapi.tools.discord;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class EmbedMessage {
    private String title, description, url;
    private Color color;

    private Footer footer;
    private String thumbnail, image;
    private Author author;
    private final java.util.List<Field> fields = new ArrayList<>();

    public EmbedMessage setTitle(String title) {
        this.title = title;
        return this;
    }

    public EmbedMessage setDescription(String description) {
        this.description = description;
        return this;
    }

    public EmbedMessage setUrl(String url) {
        this.url = url;
        return this;
    }

    public EmbedMessage setColor(Color color) {
        this.color = color;
        return this;
    }

    public EmbedMessage setFooter(String text, String icon) {
        this.footer = new Footer(text, icon);
        return this;
    }

    public EmbedMessage setThumbnail(String url) {
        this.thumbnail = url;
        return this;
    }

    public EmbedMessage setImage(String url) {
        this.image = url;
        return this;
    }

    public EmbedMessage setAuthor(String name, String url, String icon) {
        this.author = new Author(name, url, icon);
        return this;
    }

    public EmbedMessage addField(String name, String value, boolean inline) {
        this.fields.add(new Field(name, value, inline));
        return this;
    }

    protected static class Footer {
        private final String text, iconUrl;

        private Footer(String text, String iconUrl) {
            this.text = text;
            this.iconUrl = iconUrl;
        }

        protected String getText() {
            return text;
        }

        protected String getIconUrl() {
            return iconUrl;
        }
    }

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

    public String getThumbnail() {
        return thumbnail;
    }

    public String getImage() {
        return image;
    }

    public Author getAuthor() {
        return author;
    }

    public List<Field> getFields() {
        return fields;
    }

    protected static class Author {
        private final String name;
        private final String url;
        private final String iconUrl;

        private Author(String name, String url, String iconUrl) {
            this.name = name;
            this.url = url;
            this.iconUrl = iconUrl;
        }

        protected String getName() {
            return name;
        }

        protected String getUrl() {
            return url;
        }

        protected String getIconUrl() {
            return iconUrl;
        }
    }

    protected static class Field {
        private final String name;
        private final String value;
        private final boolean inline;

        private Field(String name, String value, boolean inline) {
            this.name = name;
            this.value = value;
            this.inline = inline;
        }

        protected String getName() {
            return name;
        }

        protected String getValue() {
            return value;
        }

        protected boolean isInline() {
            return inline;
        }
    }
}
