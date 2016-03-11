public void genereerSQL(){
        String jsonString = "";
        try {
            jsonString = sendPost();
        } catch (IOException ex) {
            logger.error("Ophalen gegevens Coyote is mislukt.");
            logger.error(ex);
        }

        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(jsonString, JsonObject.class);
        JsonObject e = jsonObject.getAsJsonObject("Gand");
        Set<Map.Entry<String, JsonElement>> trajecten = e.entrySet();

        try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("tempsql.txt"), "utf-8"))) {

            for (Map.Entry<String, JsonElement> traject : trajecten) {
                int optReistijd=0, afstand=0;
                String startlat="", startlong="",stoplat="", stoplong="";

                Set<Map.Entry<String, JsonElement>> coordinatendataStart = null;
                Set<Map.Entry<String, JsonElement>> coordinatendataStop = null;
                JsonArray jsonArray = new JsonArray();

                Set<Map.Entry<String, JsonElement>> trajectData = traject.getValue().getAsJsonObject().entrySet();
                for (Map.Entry<String, JsonElement> data : trajectData) {
                    switch (data.getKey()) {
                        case "normal_time":
                           optReistijd = data.getValue().getAsInt();
                            break;
                        case "length":
                            afstand = data.getValue().getAsInt();
                            break;
                        case "geometries":
                            jsonArray = data.getValue().getAsJsonArray().get(0).getAsJsonArray();
                            coordinatendataStart = data.getValue().getAsJsonArray().get(0).getAsJsonArray().get(0).getAsJsonObject().entrySet();
                            coordinatendataStop = data.getValue().getAsJsonArray().get(0).getAsJsonArray().get(data.getValue().getAsJsonArray().get(0).getAsJsonArray().size()-1).getAsJsonObject().entrySet();
                            break;
                    }
                }

                for (Map.Entry<String, JsonElement> data : coordinatendataStart) {
                    switch (data.getKey()) {
                        case "lng":
                            startlong = data.getValue().getAsString();
                            break;
                        case "lat":
                            startlat = data.getValue().getAsString();
                            break;

                    }
                }
                for (Map.Entry<String, JsonElement> data : coordinatendataStop) {
                    switch (data.getKey()) {
                        case "lng":
                            stoplong = data.getValue().getAsString();
                            break;
                        case "lat":
                            stoplat = data.getValue().getAsString();


                    }
                }

                writer.write("insert into vop.trajecten(naam,lengte,optimale_reistijd,is_active,start_latitude,start_longitude,end_latitude,end_longitude) values(\"");
                writer.write(traject.getKey());
                writer.write("\",");
                writer.write(String.valueOf(afstand));
                writer.write(",");
                writer.write(String.valueOf(optReistijd));
                writer.write(",");
                writer.write(String.valueOf(1));
                writer.write(",\"");
                writer.write(startlat);
                writer.write("\",\"");
                writer.write(startlong);
                writer.write("\",\"");
                writer.write(stoplat);
                writer.write("\",\"");
                writer.write(stoplong);
                writer.write("\"");
                writer.write(");");
                writer.write(System.getProperty("line.separator"));

                for (int i = 1; i<jsonArray.size()-1;i++){
                    String lng="",lat="";

                    writer.write("insert into vop.waypoints(volgnr, traject_id,latitude,longitude) values(");
                    writer.write(String.valueOf(i));
                    writer.write(",");
                    writer.write("(select id from vop.trajecten where naam = \"");
                    writer.write(traject.getKey());
                    writer.write("\"),");

                    for (Map.Entry<String, JsonElement> data : jsonArray.get(i).getAsJsonObject().entrySet()) {
                        switch (data.getKey()) {
                            case "lng":
                                lng = data.getValue().getAsString();
                                break;
                            case "lat":
                                lat = data.getValue().getAsString();
                        }
                    }
                    writer.write(lat);
                    writer.write(",");
                    writer.write(lng);
                    writer.write(");");
                    writer.write(System.getProperty("line.separator"));
                }
                writer.write(System.getProperty("line.separator"));

            }



        }catch(Exception ex){

        }