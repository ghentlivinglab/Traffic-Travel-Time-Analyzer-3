package be.ugent.tiwi.domein;

public enum RequestType {
    GET {
        @Override
        public String toString() {
            return "GET";
        }
    },
    POST {
        @Override
        public String toString() {
            return "POST";
        }
    }
}
