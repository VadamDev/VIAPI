package net.vadamdev.viaapi.tools.discord;

import javax.net.ssl.HttpsURLConnection;
import java.awt.*;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.List;
import java.util.*;

/**
 * Class used to execute Discord Webhooks with low effort
 * @author ?, edited by VadamDev
 */
public class DiscordWebhook {
    private final String webhookUsername;
    private final String webhookUrl;
    private String webhookAvatarUrl;

    //Content
    private String text;
    private List<EmbedObject> embeds = new ArrayList<>();

    /**
     * Constructs a new DiscordWebhook instance
     *
     * @param url The webhook URL obtained in Discord
     */
    public DiscordWebhook(String url, String webhookUsername, String webhookAvatarUrl) {
        this.webhookUrl = url;
        this.webhookUsername = webhookUsername;
        this.webhookAvatarUrl = webhookAvatarUrl;
    }

    public DiscordWebhook(String url, String webhookUsername) {
        this(url, webhookUsername, null);
    }

    public void setWebhookAvatarUrl(String webhookAvatarUrl) {
        this.webhookAvatarUrl = webhookAvatarUrl;
    }

    public void addEmbed(EmbedObject embed) {
        this.embeds.add(embed);
    }

    public void addText(String text) {
        this.text = text;
    }

    public void execute() throws IOException {
        if (this.text == null && this.embeds.isEmpty()) throw new IllegalArgumentException("Set content or add at least one EmbedObject");

        JSONObject json = new JSONObject();

        json.put("content", this.text);
        json.put("username", this.webhookUsername);
        json.put("avatar_url", this.webhookAvatarUrl);
        json.put("tts", false);

        if(!this.embeds.isEmpty()) {
            List<JSONObject> embedObjects = new ArrayList<>();

            for (EmbedObject embed : this.embeds) {
                JSONObject jsonEmbed = new JSONObject();

                jsonEmbed.put("title", embed.getTitle());
                jsonEmbed.put("description", embed.getDescription());
                jsonEmbed.put("url", embed.getUrl());

                if (embed.getColor() != null) {
                    Color color = embed.getColor();
                    int rgb = color.getRed();
                    rgb = (rgb << 8) + color.getGreen();
                    rgb = (rgb << 8) + color.getBlue();

                    jsonEmbed.put("color", rgb);
                }

                EmbedObject.Footer footer = embed.getFooter();
                EmbedObject.Image image = embed.getImage();
                EmbedObject.Thumbnail thumbnail = embed.getThumbnail();
                List<EmbedObject.Field> fields = embed.getFields();

                if(footer != null) {
                    JSONObject jsonFooter = new JSONObject();
                    jsonFooter.put("text", footer.getText());
                    jsonFooter.put("icon_url", footer.getIconUrl());

                    jsonEmbed.put("footer", jsonFooter);
                }

                if(image != null) {
                    JSONObject jsonImage = new JSONObject();
                    jsonImage.put("url", image.getUrl());

                    jsonEmbed.put("image", jsonImage);
                }

                if(thumbnail != null) {
                    JSONObject jsonThumbnail = new JSONObject();
                    jsonThumbnail.put("url", thumbnail.getUrl());

                    jsonEmbed.put("thumbnail", jsonThumbnail);
                }

                List<JSONObject> jsonFields = new ArrayList<>();
                for (EmbedObject.Field field : fields) {
                    JSONObject jsonField = new JSONObject();

                    jsonField.put("name", field.getName());
                    jsonField.put("value", field.getValue());
                    jsonField.put("inline", field.isInline());

                    jsonFields.add(jsonField);
                }

                jsonEmbed.put("fields", jsonFields.toArray());
                embedObjects.add(jsonEmbed);
            }

            json.put("embeds", embedObjects.toArray());
        }

        URL url = new URL(this.webhookUrl);
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        connection.addRequestProperty("Content-Type", "application/json");
        connection.addRequestProperty("User-Agent", "Java-DiscordWebhook-BY-Gelox_");
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");

        OutputStream stream = connection.getOutputStream();
        stream.write(json.toString().getBytes());
        stream.flush();
        stream.close();

        connection.getInputStream().close(); //I'm not sure why but it doesn't work without getting the InputStream
        connection.disconnect();
    }

    private class JSONObject {
        private final HashMap<String, Object> map = new HashMap<>();

        private void put(String key, Object value) {
            if (value != null) {
                map.put(key, value);
            }
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            Set<Map.Entry<String, Object>> entrySet = map.entrySet();
            builder.append("{");

            int i = 0;
            for (Map.Entry<String, Object> entry : entrySet) {
                Object val = entry.getValue();
                builder.append(quote(entry.getKey())).append(":");

                if (val instanceof String) {
                    builder.append(quote(String.valueOf(val)));
                } else if (val instanceof Integer) {
                    builder.append(Integer.valueOf(String.valueOf(val)));
                } else if (val instanceof Boolean) {
                    builder.append(val);
                } else if (val instanceof JSONObject) {
                    builder.append(val.toString());
                } else if (val.getClass().isArray()) {
                    builder.append("[");
                    int len = Array.getLength(val);
                    for (int j = 0; j < len; j++) {
                        builder.append(Array.get(val, j).toString()).append(j != len - 1 ? "," : "");
                    }
                    builder.append("]");
                }

                builder.append(++i == entrySet.size() ? "}" : ",");
            }

            return builder.toString();
        }

        private String quote(String string) {
            return "\"" + string + "\"";
        }
    }
}
