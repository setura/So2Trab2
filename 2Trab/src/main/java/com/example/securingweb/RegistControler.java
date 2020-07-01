package com.example.securingweb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;


@RestController
public class RegistControler extends HttpServlet {

    public static final double R = 6372.8; // In kilometers
    private RegistoRepository registoRepository;
    private UtilizadorRepository utilizadorRepository;

    @Autowired
    public RegistControler(RegistoRepository registoRepository, UtilizadorRepository utilizadorRepository) {
        this.registoRepository = registoRepository;
        this.utilizadorRepository = utilizadorRepository;
    }

    //Função da autoria de RosettaCode https://rosettacode.org/wiki/Haversine_formula#Java
    //Calcular a distancia dos pontos
    public static double haversine(double lat1, double lon1, double lat2, double lon2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        double a = Math.pow(Math.sin(dLat / 2), 2) + Math.pow(Math.sin(dLon / 2), 2) * Math.cos(lat1) * Math.cos(lat2);
        double c = 2 * Math.asin(Math.sqrt(a));
        return R * c;
    }

    @PostMapping("/user/add")
    public RedirectView addUser(@RequestParam(name = "name") String name,
                                @RequestParam(name = "pass") String password) {
        if (!utilizadorRepository.existsByUserName(name)) {
            utilizadorRepository.save(new Utilizador(name, new BCryptPasswordEncoder().encode(password), "RULE_USER"));
            return new RedirectView("/login");
        }
        return new RedirectView("/adduser");

    }

    @GetMapping("/locals")
    public String getLocals() throws IOException {
        try {
            registoRepository.deleteLastHour();
        } catch (Exception e) {
        }
        return makeTable();
    }

    public String makeTable() {
        Iterable<Registo> all = registoRepository.getCompleteTable();
        String lines = "";
        for (Registo registo : all) {
            lines += ("<tr>\n" +
                    "    <td>" + registo.localName + "</td>\n" +
                    "    <td>" + registo.latitude + "</td>\n" +
                    "    <td>" + registo.longitude + "</td>\n" +
                    "    <td>" + registo.emp_ty + "</td>\n" +
                    "    <td>" + registo.few_people + "</td>\n" +
                    "    <td>" + registo.fu_ll + "</td>\n" +
                    "    <td>" + registo.full_w_queue + "</td>\n" +
                    "  </tr>");
        }

        //PrintWriter out = response.getWriter();
        return "<html>\n" +
                "<head>\n" +
                "<link rel=\"stylesheet\" type=\"text/css\" href=\"css/table_css.css\">\n" +
                "<link rel=\"stylesheet\" href=\"https://unpkg.com/leaflet@1.6.0/dist/leaflet.css\"\n" +
                "   integrity=\"sha512-xwE/Az9zrjBIphAcBb3F6JVqxf46+CDLwfLMHloNu6KEQCAWi6HcDUbeOfBIptF7tcCzusKFjFw2yuvEpDL9wQ==\"\n" +
                "   crossorigin=\"\"/>\n" +
                "<script src=\"https://unpkg.com/leaflet@1.6.0/dist/leaflet.js\"\n" +
                "   integrity=\"sha512-gZwIG9x3wUXg2hdXF6+rVkLF/0Vi9U8D2Ntg4Ga5I5BZpVkVxlJWbSQtXPSiUTtC0TjtGOmxa1AJPuV0CPthew==\"\n" +
                "   crossorigin=\"\"></script>" +
                "</head>\n" +
                ("<body>\n") + "<div class=\"header\">\n" +
                "        <a href=\"/home\" class=\"logo\">PlaceStatus</a>\n" +
                "    </div>\n" +
                " <div id=\"mapid\" style=\"width: auto; height: 400px; position: relative; outline: none;border-radius: 10px;margin-bottom: 10px;\" class=\"leaflet-container leaflet-retina leaflet-fade-anim leaflet-grab leaflet-touch-drag\" tabindex=\"0\"><div class=\"leaflet-pane leaflet-map-pane\" style=\"transform: translate3d(0px, 0px, 0px);\"><div class=\"leaflet-pane leaflet-tile-pane\"><div class=\"leaflet-layer \" style=\"z-index: 1; opacity: 1;\"><div class=\"leaflet-tile-container leaflet-zoom-animated\" style=\"z-index: 18; transform: translate3d(0px, 0px, 0px) scale(1);\"><img alt=\"\" role=\"presentation\" src=\"https://api.mapbox.com/styles/v1/mapbox/streets-v11/tiles/12/2046/1361?access_token=pk.eyJ1IjoibWFwYm94IiwiYSI6ImNpejY4NXVycTA2emYycXBndHRqcmZ3N3gifQ.rJcFIG214AriISLbB6B5aw\" class=\"leaflet-tile leaflet-tile-loaded\" style=\"width: 512px; height: 512px; transform: translate3d(-200px, -347px, 0px); opacity: 1;\"><img alt=\"\" role=\"presentation\" src=\"https://api.mapbox.com/styles/v1/mapbox/streets-v11/tiles/12/2047/1361?access_token=pk.eyJ1IjoibWFwYm94IiwiYSI6ImNpejY4NXVycTA2emYycXBndHRqcmZ3N3gifQ.rJcFIG214AriISLbB6B5aw\" class=\"leaflet-tile leaflet-tile-loaded\" style=\"width: 512px; height: 512px; transform: translate3d(312px, -347px, 0px); opacity: 1;\"><img alt=\"\" role=\"presentation\" src=\"https://api.mapbox.com/styles/v1/mapbox/streets-v11/tiles/12/2046/1362?access_token=pk.eyJ1IjoibWFwYm94IiwiYSI6ImNpejY4NXVycTA2emYycXBndHRqcmZ3N3gifQ.rJcFIG214AriISLbB6B5aw\" class=\"leaflet-tile leaflet-tile-loaded\" style=\"width: 512px; height: 512px; transform: translate3d(-200px, 165px, 0px); opacity: 1;\"><img alt=\"\" role=\"presentation\" src=\"https://api.mapbox.com/styles/v1/mapbox/streets-v11/tiles/12/2047/1362?access_token=pk.eyJ1IjoibWFwYm94IiwiYSI6ImNpejY4NXVycTA2emYycXBndHRqcmZ3N3gifQ.rJcFIG214AriISLbB6B5aw\" class=\"leaflet-tile leaflet-tile-loaded\" style=\"width: 512px; height: 512px; transform: translate3d(312px, 165px, 0px); opacity: 1;\"></div></div></div><div class=\"leaflet-pane leaflet-shadow-pane\"></div><div class=\"leaflet-pane leaflet-overlay-pane\"></div><div class=\"leaflet-pane leaflet-marker-pane\"></div><div class=\"leaflet-pane leaflet-tooltip-pane\"></div><div class=\"leaflet-pane leaflet-popup-pane\"></div><div class=\"leaflet-proxy leaflet-zoom-animated\" style=\"transform: translate3d(1.04805e+06px, 697379px, 0px) scale(4096);\"></div></div><div class=\"leaflet-control-container\"><div class=\"leaflet-top leaflet-left\"><div class=\"leaflet-control-zoom leaflet-bar leaflet-control\"><a class=\"leaflet-control-zoom-in\" href=\"#\" title=\"Zoom in\" role=\"button\" aria-label=\"Zoom in\">+</a><a class=\"leaflet-control-zoom-out\" href=\"#\" title=\"Zoom out\" role=\"button\" aria-label=\"Zoom out\">−</a></div></div><div class=\"leaflet-top leaflet-right\"></div><div class=\"leaflet-bottom leaflet-left\"></div><div class=\"leaflet-bottom leaflet-right\"><div class=\"leaflet-control-attribution leaflet-control\"><a href=\"https://leafletjs.com\" title=\"A JS library for interactive maps\">Leaflet</a> | Map data © <a href=\"https://www.openstreetmap.org/\">OpenStreetMap</a> contributors, <a href=\"https://creativecommons.org/licenses/by-sa/2.0/\">CC-BY-SA</a>, Imagery © <a href=\"https://www.mapbox.com/\">Mapbox</a></div></div></div></div>" +
                "<script>\n" +
                "\n" +
                "\tvar mymap = L.map('mapid').setView([38.713108, -9.137535], 13);\n" +
                addLocalToMap() +

                "\n" +
                "\t\tfor (var i = 0; i < locals.length; i++) {\n" +
                "\t\t\tmarker = new L.marker([locals[i][1],locals[i][2]])\n" +

                ".bindPopup(\"<b>\"+locals[i][0]+\"</b><br>Empty: \"+locals[i][3] +\"<br>Few people: \"+locals[i][4]+\"<br>Full: \"+locals[i][5] +\"<br>Full w/Queue: \"+locals[i][6])" +
                ".\taddTo(mymap);\n" +
                "\t\t}" +
                "\n" +
                "\tL.tileLayer('https://api.mapbox.com/styles/v1/{id}/tiles/{z}/{x}/{y}?access_token=pk.eyJ1IjoibWFwYm94IiwiYSI6ImNpejY4NXVycTA2emYycXBndHRqcmZ3N3gifQ.rJcFIG214AriISLbB6B5aw', {\n" +
                "\t\tmaxZoom: 18,\n" +
                "\t\tattribution: 'Map data &copy; <a href=\"https://www.openstreetmap.org/\">OpenStreetMap</a> contributors, ' +\n" +
                "\t\t\t'<a href=\"https://creativecommons.org/licenses/by-sa/2.0/\">CC-BY-SA</a>, ' +\n" +
                "\t\t\t'Imagery © <a href=\"https://www.mapbox.com/\">Mapbox</a>',\n" +
                "\t\tid: 'mapbox/streets-v11',\n" +
                "\t\ttileSize: 512,\n" +
                "\t\tzoomOffset: -1\n" +
                "\t}).addTo(mymap);\n" +
                "\n" +
                "</script>" +
                "<div class=\"forma\" style=\"/* FONT-WEIGHT: 100; *//* position: unset; *//* z-index: 1; */background: #FFFFFF;margin-bottom: 10px;/* padding: 0.0px; */border-radius: 10px;/* text-align: -webkit-center; */box-shadow: -1px 0px 20px 0 rgba(0, 0, 0, 0.2), 0 5px 5px 0 rgba(0, 0, 0, 0.24);/* height: 30; */align-content: unset;vertical-align: middle;text-align: center;font-size: small;padding: 3px;width: fit-content;\">\n" +
                "     \n" +
                "        <text>Be aware that records older than 1 hour are automatically deleted.</text>\n" +
                "     \n" +
                "</div>" +
                ("<div class=\"form\">\n") +
                ("<table>\n" +
                        "  <tr>\n" +
                        "    <th>Place</th>\n" +
                        "    <th>Latitude</th>\n" +
                        "    <th>Longitude</th>\n" +
                        "    <th>Empty</th>\n" +
                        "    <th>With few people</th>\n" +
                        "    <th>Full</th>\n" +
                        "    <th>Full with queue</th>\n" +
                        "  </tr>") + lines + "  </tr>" +
                "</table>\n" +
                "\n" + "</div>\n" +
                "</body>\n" +
                "</html>";


    }

    private String addLocalToMap() {
        Iterable<Registo> locals = registoRepository.getCompleteTable();
        String returnString = "var locals =[ ";

        Iterator<Registo> iterador = locals.iterator();
        for (Registo registo : locals) {
            returnString += '[' + "\"" + registo.localName + "\"" + ',' + registo.latitude + ',' + registo.longitude + ',' + registo.emp_ty + ',' + registo.few_people + ',' + registo.fu_ll + ',' + registo.full_w_queue + ']';
            if (iterador.hasNext()) {
                try {
                    iterador.next();
                } catch (RuntimeException targetException) {
                }

                returnString += ",";
            }
        }
        returnString += ']';
        return returnString;
    }

    @RequestMapping(value = "/locals/add", method = RequestMethod.POST)
    public RedirectView addLocal(@RequestParam(name = "localName") String name,
                                 @RequestParam(name = "type") String value,
                                 @RequestParam(name = "long") String lon,
                                 @RequestParam(name = "lat") String lat) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        System.out.println("USER:" + currentPrincipalName);//erro aqui devolve NULL
        int typeValue = Integer.parseInt(value);
        Double longitude = Double.parseDouble(lon);
        Double latitude = Double.parseDouble(lat);
        registoRepository.save(new Registo(currentPrincipalName, latitude, longitude, name, typeValue));

        return new RedirectView("/home");

    }

    @GetMapping("/userReg")
    public String getUserRegist() {
        //return registoRepository.findAllByByuserId((long)2);
        return makeUserTable();
    }

    private String makeUserTable() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        Iterable<Registo> all = registoRepository.findByuserId(currentPrincipalName);
        String lines = "";
        for (Registo registo : all) {
            lines += ("<tr>\n" +
                    "    <td>" + registo.getRegId() + "</td>\n" +
                    "    <td>" + registo.getLocalName() + "</td>\n" +
                    "    <td>" + registo.decodeStituation() + "</td>\n" +
                    "    <td>" + registo.getLongitude() + "</td>\n" +
                    "    <td>" + registo.getLatitude() + "</td>\n" +
                    "  </tr>");
        }
        //PrintWriter out = response.getWriter();
        return "<html lang=\"en\" xmlns:th=\"http://www.w3.org/1999/xhtml\">\n" +
                "<head>\n" +
                "<style>\n" +
                "table {\n" +
                "  font-family: arial, sans-serif;\n" +
                "  border-collapse: collapse;\n" +
                "  width: 100%;\n" +
                "}\n" +
                "\n" +
                "td, th {\n" +
                "  border: 1px solid #dddddd;\n" +
                "  text-align: left;\n" +
                "  padding: 8px;\n" +
                "}\n" +
                "\n" +
                "tr:nth-child(even) {\n" +
                "  background-color: #dddddd;\n" +
                "}\n" +
                "</style>\n" +
                "</head>"
                + ("<body>\n") +
                ("<table>\n" +
                        "  <tr>\n" +
                        "    <th>RegistId</th>\n" +
                        "    <th>Place</th>\n" +
                        "    <th>Situation</th>\n" +
                        "    <th>Longitude</th>\n" +
                        "    <th>Latitude</th>\n" +
                        "  </tr>") + lines +
                "</table>\n" +
                "\n" +
                "</body>\n" +
                "</html>";

    }

    /*@GetMapping("/error")
    public RedirectView errorPage()
    {
        return new RedirectView("/error");
    }*/

    @RequestMapping(value = "/userPanel/delete", method = RequestMethod.POST)
    public RedirectView deleteUser(@RequestParam(name = "idDelete") String id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        long idParsed = Long.parseLong(id);
        Iterable<Registo> userRegist = registoRepository.findByuserId(currentPrincipalName);
        for (Registo regist : userRegist) {
            if (regist.getRegId() == idParsed) {
                registoRepository.deleteById(idParsed);
                return new RedirectView("/delete");
            }
        }
        return new RedirectView("/home");


    }

    @PostMapping("/near/get")
    public String getNearMarker(@RequestParam(name = "lat") double lat, @RequestParam(name = "long") double longitude) {
        try {
            registoRepository.deleteLastHour();
        } catch (Exception e) {
        }
        //meter aqui novz fun que ia apagar o registos atrasados
        return makeNearTable(lat, longitude);
    }

    public int compare(Registo r1, Registo r2) {
        return Double.compare(r1.dist, r2.dist);
    }

    public String makeNearTable(double lat, double longitude) {
        //response.setContentType("text/html");
        Iterable<Registo> all = null;
        ArrayList<Registo> sorted = new ArrayList<>();
        String setView = changeSetView(lat, longitude);
        try {
            all = registoRepository.findClossest(lat, longitude);
            for (Registo regs : all) {
                regs.dist = haversine(lat, longitude, regs.latitude, regs.longitude);
                sorted.add(regs);

            }
            sorted.sort(this::compare);

        } catch (Exception e) {
            e.printStackTrace();
        }

        String lines = "";
        if (sorted.size() > 0) {
            setView = changeSetView(sorted.get(0).latitude, sorted.get(0).longitude);
        }
        for (Registo registo : sorted) {
            lines += ("<tr>\n" +
                    "    <td>" + registo.localName + "</td>\n" +
                    "    <td>" + registo.latitude + "</td>\n" +
                    "    <td>" + registo.longitude + "</td>\n" +
                    "    <td>" + registo.emp_ty + "</td>\n" +
                    "    <td>" + registo.few_people + "</td>\n" +
                    "    <td>" + registo.fu_ll + "</td>\n" +
                    "    <td>" + registo.full_w_queue + "</td>\n" +
                    "    <td>" + registo.dist + "</td>\n" +
                    "  </tr>");
        }

        //PrintWriter out = response.getWriter();
        return "<html>\n" +
                "<head>\n" +
                "<link rel=\"stylesheet\" type=\"text/css\" href=\"/css/table_css.css\">\n" +
                "<link rel=\"stylesheet\" href=\"https://unpkg.com/leaflet@1.6.0/dist/leaflet.css\"\n" +
                "   integrity=\"sha512-xwE/Az9zrjBIphAcBb3F6JVqxf46+CDLwfLMHloNu6KEQCAWi6HcDUbeOfBIptF7tcCzusKFjFw2yuvEpDL9wQ==\"\n" +
                "   crossorigin=\"\"/>\n" +
                "<script src=\"https://unpkg.com/leaflet@1.6.0/dist/leaflet.js\"\n" +
                "   integrity=\"sha512-gZwIG9x3wUXg2hdXF6+rVkLF/0Vi9U8D2Ntg4Ga5I5BZpVkVxlJWbSQtXPSiUTtC0TjtGOmxa1AJPuV0CPthew==\"\n" +
                "   crossorigin=\"\"></script>" +
                "</head>\n" +
                ("<body>\n") + "<div class=\"header\">\n" +
                "        <a href=\"/home\" class=\"logo\">PlaceStatus</a>\n" +

                "    </div>\n" +
                " <div id=\"mapid\" style=\"width: auto; height: 400px; position: relative; outline: none; border-radius: 10px;margin-bottom: 10px;\" class=\"leaflet-container leaflet-retina leaflet-fade-anim leaflet-grab leaflet-touch-drag\" tabindex=\"0\"><div class=\"leaflet-pane leaflet-map-pane\" style=\"transform: translate3d(0px, 0px, 0px);\"><div class=\"leaflet-pane leaflet-tile-pane\"><div class=\"leaflet-layer \" style=\"z-index: 1; opacity: 1;\"><div class=\"leaflet-tile-container leaflet-zoom-animated\" style=\"z-index: 18; transform: translate3d(0px, 0px, 0px) scale(1);\"><img alt=\"\" role=\"presentation\" src=\"https://api.mapbox.com/styles/v1/mapbox/streets-v11/tiles/12/2046/1361?access_token=pk.eyJ1IjoibWFwYm94IiwiYSI6ImNpejY4NXVycTA2emYycXBndHRqcmZ3N3gifQ.rJcFIG214AriISLbB6B5aw\" class=\"leaflet-tile leaflet-tile-loaded\" style=\"width: 512px; height: 512px; transform: translate3d(-200px, -347px, 0px); opacity: 1;\"><img alt=\"\" role=\"presentation\" src=\"https://api.mapbox.com/styles/v1/mapbox/streets-v11/tiles/12/2047/1361?access_token=pk.eyJ1IjoibWFwYm94IiwiYSI6ImNpejY4NXVycTA2emYycXBndHRqcmZ3N3gifQ.rJcFIG214AriISLbB6B5aw\" class=\"leaflet-tile leaflet-tile-loaded\" style=\"width: 512px; height: 512px; transform: translate3d(312px, -347px, 0px); opacity: 1;\"><img alt=\"\" role=\"presentation\" src=\"https://api.mapbox.com/styles/v1/mapbox/streets-v11/tiles/12/2046/1362?access_token=pk.eyJ1IjoibWFwYm94IiwiYSI6ImNpejY4NXVycTA2emYycXBndHRqcmZ3N3gifQ.rJcFIG214AriISLbB6B5aw\" class=\"leaflet-tile leaflet-tile-loaded\" style=\"width: 512px; height: 512px; transform: translate3d(-200px, 165px, 0px); opacity: 1;\"><img alt=\"\" role=\"presentation\" src=\"https://api.mapbox.com/styles/v1/mapbox/streets-v11/tiles/12/2047/1362?access_token=pk.eyJ1IjoibWFwYm94IiwiYSI6ImNpejY4NXVycTA2emYycXBndHRqcmZ3N3gifQ.rJcFIG214AriISLbB6B5aw\" class=\"leaflet-tile leaflet-tile-loaded\" style=\"width: 512px; height: 512px; transform: translate3d(312px, 165px, 0px); opacity: 1;\"></div></div></div><div class=\"leaflet-pane leaflet-shadow-pane\"></div><div class=\"leaflet-pane leaflet-overlay-pane\"></div><div class=\"leaflet-pane leaflet-marker-pane\"></div><div class=\"leaflet-pane leaflet-tooltip-pane\"></div><div class=\"leaflet-pane leaflet-popup-pane\"></div><div class=\"leaflet-proxy leaflet-zoom-animated\" style=\"transform: translate3d(1.04805e+06px, 697379px, 0px) scale(4096);\"></div></div><div class=\"leaflet-control-container\"><div class=\"leaflet-top leaflet-left\"><div class=\"leaflet-control-zoom leaflet-bar leaflet-control\"><a class=\"leaflet-control-zoom-in\" href=\"#\" title=\"Zoom in\" role=\"button\" aria-label=\"Zoom in\">+</a><a class=\"leaflet-control-zoom-out\" href=\"#\" title=\"Zoom out\" role=\"button\" aria-label=\"Zoom out\">−</a></div></div><div class=\"leaflet-top leaflet-right\"></div><div class=\"leaflet-bottom leaflet-left\"></div><div class=\"leaflet-bottom leaflet-right\"><div class=\"leaflet-control-attribution leaflet-control\"><a href=\"https://leafletjs.com\" title=\"A JS library for interactive maps\">Leaflet</a> | Map data © <a href=\"https://www.openstreetmap.org/\">OpenStreetMap</a> contributors, <a href=\"https://creativecommons.org/licenses/by-sa/2.0/\">CC-BY-SA</a>, Imagery © <a href=\"https://www.mapbox.com/\">Mapbox</a></div></div></div></div>" +
                "<script>\n" +
                "\n" +
                setView +
                addLocalToMap() +

                "\n" +
                "\t\tfor (var i = 0; i < locals.length; i++) {\n" +
                "\t\t\tmarker = new L.marker([locals[i][1],locals[i][2]])\n" +

                ".bindPopup(\"<b>\"+locals[i][0]+\"</b><br>Empty: \"+locals[i][3] +\"<br>Few people: \"+locals[i][4]+\"<br>Full: \"+locals[i][5] +\"<br>Full w/Queue: \"+locals[i][6])" +
                ".\taddTo(mymap);\n" +
                "\t\t}" +
                "\n" +
                "\tL.tileLayer('https://api.mapbox.com/styles/v1/{id}/tiles/{z}/{x}/{y}?access_token=pk.eyJ1IjoibWFwYm94IiwiYSI6ImNpejY4NXVycTA2emYycXBndHRqcmZ3N3gifQ.rJcFIG214AriISLbB6B5aw', {\n" +
                "\t\tmaxZoom: 18,\n" +
                "\t\tattribution: 'Map data &copy; <a href=\"https://www.openstreetmap.org/\">OpenStreetMap</a> contributors, ' +\n" +
                "\t\t\t'<a href=\"https://creativecommons.org/licenses/by-sa/2.0/\">CC-BY-SA</a>, ' +\n" +
                "\t\t\t'Imagery © <a href=\"https://www.mapbox.com/\">Mapbox</a>',\n" +
                "\t\tid: 'mapbox/streets-v11',\n" +
                "\t\ttileSize: 512,\n" +
                "\t\tzoomOffset: -1\n" +
                "\t}).addTo(mymap);\n" +
                "\n" +
                "</script>" +
                ("<div class=\"form\">\n") +
                ("<table>\n" +
                        "  <tr>\n" +
                        "    <th>Place</th>\n" +
                        "    <th>Latitude</th>\n" +
                        "    <th>Longitude</th>\n" +
                        "    <th>Empty</th>\n" +
                        "    <th>With few people</th>\n" +
                        "    <th>Full</th>\n" +
                        "    <th>Full with queue</th>\n" +
                        "    <th>Dist. in kilometers</th>\n" +
                        "  </tr>") + lines + "  </tr>" +
                "</table>\n" +
                "\n" + "</div>\n" +
                "</body>\n" +
                "</html>";


    }

    private String changeSetView(double lat, double longi) {
        return "\tvar mymap = L.map('mapid').setView([" + lat + ", " + longi + "], 13);\n";
    }

}
