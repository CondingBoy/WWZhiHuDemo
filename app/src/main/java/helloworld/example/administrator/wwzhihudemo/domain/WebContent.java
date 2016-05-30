package helloworld.example.administrator.wwzhihudemo.domain;

import java.util.Arrays;

/**
 * Created by Administrator on 2016/5/30.
 */
public class WebContent {
    public String body;
    public String[] css;
    public String ga_prefix;
    public String id;
    public String image;
    public String image_source;
    public String[] images;
    public String[] js;
    public String share_url;
    public String title;
    public String type;

    @Override
    public String toString() {
        return "WebContent{"  +
                ", css=" + Arrays.toString(css) +
                ", id='" + id + '\'' +
                ", image='" + image + '\'' +
                ", image_source='" + image_source + '\'' +
                ", images=" + Arrays.toString(images) +
                ", title='" + title + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
