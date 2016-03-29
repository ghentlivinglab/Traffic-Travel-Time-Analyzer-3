package be.ugent.tiwi.domein;

public enum RequestType {
    /**
     * De GET request-type.
     */
    GET {
        @Override
        public String toString() {
            return "GET";
        }
    },
    /**
     * De POST request-type.
     */
    POST {
        @Override
        public String toString() {
            return "POST";
        }
    }
}
