package net.trippedout.giphyandroidlib;

/**
 * Container for all the different possible responses from GIPHY, reusing as much as possible.
 *
 * Public API on top, with all inner classes below. Note that the main class is typically just
 * encapsulating the <i>T</i>Data class, since all responses have that silly "data:" attribute
 * at the top.
 *
 * TODO - need custom processor so that we dont have to lug .data keys around
 *
 */
public class Responses {

    /**
     * Main response from our /translate/ api call.
     */
    public static class Translate {
        public final TranslateData data;

        public Translate(TranslateData data) {
            this.data = data;
        }

        @Override
        public String toString() {
            return data.toString();
        }
    }

    public static class TranslateData {
        public final String id;
        public final String url;
        public final String rating;
        public final Images images;

        public TranslateData(String id, String url, String rating, Images images) {
            this.id = id;
            this.url = url;
            this.rating = rating;
            this.images = images;
        }

        @Override
        public String toString() {
            return "TranslateData{" +
                    "id='" + id + '\'' +
                    ", url='" + url + '\'' +
                    ", rating='" + rating + '\'' +
                    ", images=" + images.toString() +
                    '}';
        }
    }

    public static class Images {
        public final Image original;
        public final Image original_still;
        public final Image fixed_height_downsampled;

        public Images(Image original, Image original_still, Image fixed_height_downsampled) {
            this.original = original;
            this.original_still = original_still;
            this.fixed_height_downsampled = fixed_height_downsampled;
        }

        @Override
        public String toString() {
            return "Images{" +
                    "original=" + original +
                    '}';
        }
    }

    public static class Image {
        public final String url;
        public final String width;
        public final String height;
        public final String size;

        public Image(String url, String width, String height, String size) {
            this.url = url;
            this.width = width;
            this.height = height;
            this.size = size;
        }

        @Override
        public String toString() {
            return "Image{" +
                    "url='" + url + '\'' +
                    ", width='" + width + '\'' +
                    ", height='" + height + '\'' +
                    ", size='" + size + '\'' +
                    '}';
        }
    }

}
