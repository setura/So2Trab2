package com.example.securingweb;

import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;


@RestController
public class RegistControler extends HttpServlet {

    private RegistoRepository registoRepository;
    private UtilizadorRepository utilizadorRepository;

    @Autowired
    public RegistControler(RegistoRepository registoRepository,UtilizadorRepository utilizadorRepository){
        this.registoRepository=registoRepository;
        this.utilizadorRepository=utilizadorRepository;
    }

    @PostMapping("/user/add")
    public String addUser(@RequestParam(name = "name") String name,
                          @RequestParam(name = "pass") String password)
    {
       if( !utilizadorRepository.existsByUserName(name)){
            utilizadorRepository.save(new Utilizador(name,new BCryptPasswordEncoder().encode(password),"RULE_USER"));
            return "";
        }
       else return "ERROR";

    }

    @GetMapping("/locals")
    public String getLocals() throws IOException {
        try{
            registoRepository.deleteLastHour();
        }catch (Exception e) {}

        //meter aqui novz fun que ia apagar o registos atrasados
       return makeTable();
    }

    public String makeTable(){
        //response.setContentType("text/html");
        Iterable<Registo> all = registoRepository.getTest();
        String lines="";
        Registo nextReg;
        Registo reg;
        boolean flag,reg0,reg1,reg2;
        /*while (all.iterator().hasNext())
        {
            reg= all.iterator().next();
            reg
        }*/

        flag = true;
        reg0=false;
        reg1=false;
        reg2=false;

        Iterator<Registo> iterador = all.iterator();
        try{
            iterador.next();
        }catch (RuntimeException targetException) {}

        for (Registo registo : all) {

            if (iterador.hasNext())
            {
                nextReg = iterador.next();//proximo registo
            }else{
                nextReg=null;
            }

            if(flag){//Se é novo local
                lines +=("<tr>\n" +
                        "    <td>" + registo.getLocalName() + "</td>\n" +
                        "    <td>" + registo.getLongitude() + "</td>\n" +
                        "    <td>" + registo.getLatitude() + "</td>\n" );
                flag=false;
            }
            if ( nextReg==null ||(nextReg.localName.compareTo(registo.localName)!=0 ||
                    nextReg.longitude != registo.longitude ||
                    nextReg.latitude != registo.latitude))//se o proximo registo for diferente ficamos alertados para o proximo registo
            {
                flag=true;

            }

            if (registo.regType == 0){
                reg0=true;
                lines+="    <td>" + registo.count + "</td>\n";
                if(flag)
                {
                    lines+="    <td> 0 </td>\n";
                    lines+="    <td> 0 </td>\n";
                    lines+="    <td> 0 </td>\n";

                }
            }else if(registo.regType == 1)
            {
                reg1=true;
                if (!reg0)
                {
                    lines+="    <td> 0 </td>\n";
                    reg0=true;
                }
                lines+="    <td>" + registo.count + "</td>\n";

                if(flag)
                {
                    lines+="    <td> 0 </td>\n";
                    lines+="    <td> 0 </td>\n";

                }

            }else if(registo.regType == 2){
                reg2=true;
                if (!reg0 )
                {
                    lines+="    <td> 0 </td>\n";
                    reg0=true;
                }
                if(!reg1)
                {
                    lines+="    <td> 0 </td>\n";
                    reg1=true;
                }
                lines+="    <td>" + registo.count + "</td>\n";
                if(flag)
                {
                    lines+="    <td> 0 </td>\n";

                }

            }
            else if(registo.regType == 3){
                if (!reg0)
                {
                    lines+="    <td> 0 </td>\n";

                }
                if(!reg1)
                {
                    lines+="    <td> 0 </td>\n";

                }
                if (!reg2)
                {
                    lines+="    <td> 0 </td>\n";
                }
                lines+="    <td>" + registo.count + "</td>\n";

            }
            if ( nextReg==null ||(nextReg.localName.compareTo(registo.localName)!=0 ||
                    nextReg.longitude != registo.longitude ||
                    nextReg.latitude != registo.latitude))//se o proximo registo for diferente ficamos alertados para o proximo registo
            {
                reg0=false;
                reg1=false;
                reg2=false;
            }


        }





        //PrintWriter out = response.getWriter();
        return "<html>\n" +
                "<head>\n" +
                "<link rel=\"stylesheet\" type=\"text/css\" href=\"css/table_css.css\">\n"+
                "<link rel=\"stylesheet\" href=\"https://unpkg.com/leaflet@1.6.0/dist/leaflet.css\"\n" +
                "   integrity=\"sha512-xwE/Az9zrjBIphAcBb3F6JVqxf46+CDLwfLMHloNu6KEQCAWi6HcDUbeOfBIptF7tcCzusKFjFw2yuvEpDL9wQ==\"\n" +
                "   crossorigin=\"\"/>\n" +
                "<script src=\"https://unpkg.com/leaflet@1.6.0/dist/leaflet.js\"\n" +
                "   integrity=\"sha512-gZwIG9x3wUXg2hdXF6+rVkLF/0Vi9U8D2Ntg4Ga5I5BZpVkVxlJWbSQtXPSiUTtC0TjtGOmxa1AJPuV0CPthew==\"\n" +
                "   crossorigin=\"\"></script>"+
                "</head>\n"  +
                ("<body>\n")+"<div class=\"header\">\n" +
                "        <a href=\"/home\" class=\"logo\">CompanyLogo</a>\n" +
                "        <div class=\"header-right\">\n" +
                "            <a class=\"active\" href=\"/userPanel\">Control Panel</a>\n" +
                "            <a class=\"active\">\n" +
                "            <form th:action=\"@{/logout}\" method=\"post\">\n" +
                "                    <button>Sign Out</button>\n" +
                "                </form>\n" +
                "            </a>\n" +
                "        </div>\n" +
                "    </div>\n"+
                " <div id=\"mapid\" style=\"width: 600px; height: 400px; position: relative; outline: none;\" class=\"leaflet-container leaflet-retina leaflet-fade-anim leaflet-grab leaflet-touch-drag\" tabindex=\"0\"><div class=\"leaflet-pane leaflet-map-pane\" style=\"transform: translate3d(0px, 0px, 0px);\"><div class=\"leaflet-pane leaflet-tile-pane\"><div class=\"leaflet-layer \" style=\"z-index: 1; opacity: 1;\"><div class=\"leaflet-tile-container leaflet-zoom-animated\" style=\"z-index: 18; transform: translate3d(0px, 0px, 0px) scale(1);\"><img alt=\"\" role=\"presentation\" src=\"https://api.mapbox.com/styles/v1/mapbox/streets-v11/tiles/12/2046/1361?access_token=pk.eyJ1IjoibWFwYm94IiwiYSI6ImNpejY4NXVycTA2emYycXBndHRqcmZ3N3gifQ.rJcFIG214AriISLbB6B5aw\" class=\"leaflet-tile leaflet-tile-loaded\" style=\"width: 512px; height: 512px; transform: translate3d(-200px, -347px, 0px); opacity: 1;\"><img alt=\"\" role=\"presentation\" src=\"https://api.mapbox.com/styles/v1/mapbox/streets-v11/tiles/12/2047/1361?access_token=pk.eyJ1IjoibWFwYm94IiwiYSI6ImNpejY4NXVycTA2emYycXBndHRqcmZ3N3gifQ.rJcFIG214AriISLbB6B5aw\" class=\"leaflet-tile leaflet-tile-loaded\" style=\"width: 512px; height: 512px; transform: translate3d(312px, -347px, 0px); opacity: 1;\"><img alt=\"\" role=\"presentation\" src=\"https://api.mapbox.com/styles/v1/mapbox/streets-v11/tiles/12/2046/1362?access_token=pk.eyJ1IjoibWFwYm94IiwiYSI6ImNpejY4NXVycTA2emYycXBndHRqcmZ3N3gifQ.rJcFIG214AriISLbB6B5aw\" class=\"leaflet-tile leaflet-tile-loaded\" style=\"width: 512px; height: 512px; transform: translate3d(-200px, 165px, 0px); opacity: 1;\"><img alt=\"\" role=\"presentation\" src=\"https://api.mapbox.com/styles/v1/mapbox/streets-v11/tiles/12/2047/1362?access_token=pk.eyJ1IjoibWFwYm94IiwiYSI6ImNpejY4NXVycTA2emYycXBndHRqcmZ3N3gifQ.rJcFIG214AriISLbB6B5aw\" class=\"leaflet-tile leaflet-tile-loaded\" style=\"width: 512px; height: 512px; transform: translate3d(312px, 165px, 0px); opacity: 1;\"></div></div></div><div class=\"leaflet-pane leaflet-shadow-pane\"></div><div class=\"leaflet-pane leaflet-overlay-pane\"></div><div class=\"leaflet-pane leaflet-marker-pane\"></div><div class=\"leaflet-pane leaflet-tooltip-pane\"></div><div class=\"leaflet-pane leaflet-popup-pane\"></div><div class=\"leaflet-proxy leaflet-zoom-animated\" style=\"transform: translate3d(1.04805e+06px, 697379px, 0px) scale(4096);\"></div></div><div class=\"leaflet-control-container\"><div class=\"leaflet-top leaflet-left\"><div class=\"leaflet-control-zoom leaflet-bar leaflet-control\"><a class=\"leaflet-control-zoom-in\" href=\"#\" title=\"Zoom in\" role=\"button\" aria-label=\"Zoom in\">+</a><a class=\"leaflet-control-zoom-out\" href=\"#\" title=\"Zoom out\" role=\"button\" aria-label=\"Zoom out\">−</a></div></div><div class=\"leaflet-top leaflet-right\"></div><div class=\"leaflet-bottom leaflet-left\"></div><div class=\"leaflet-bottom leaflet-right\"><div class=\"leaflet-control-attribution leaflet-control\"><a href=\"https://leafletjs.com\" title=\"A JS library for interactive maps\">Leaflet</a> | Map data © <a href=\"https://www.openstreetmap.org/\">OpenStreetMap</a> contributors, <a href=\"https://creativecommons.org/licenses/by-sa/2.0/\">CC-BY-SA</a>, Imagery © <a href=\"https://www.mapbox.com/\">Mapbox</a></div></div></div></div>"+
                "<script>\n" +
                "\n" +
                "\tvar mymap = L.map('mapid').setView([51.505, -0.09], 13);\n" +
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
                "</script>"+
                ("<div class=\"form\">\n")+
                ("<table>\n" +
                "  <tr>\n" +
                "    <th>Place</th>\n" +
                "    <th>Longitude</th>\n" +
                "    <th>Latitude</th>\n" +
                "    <th>Empty</th>\n" +
                "    <th>With few people</th>\n" +
                "    <th>Full</th>\n" +
                "    <th>Full with queue</th>\n"+
                "  </tr>")+lines+"  </tr>"+
                "</table>\n" +
                "\n" + "</div>\n" +
                "</body>\n" +
                "</html>";


    }

    @RequestMapping(value="/locals/add",method = RequestMethod.POST)
    public String addLocal(@RequestParam(name = "localName") String name,
                         @RequestParam(name = "type") String value,
                        @RequestParam(name = "long") String lon,
                        @RequestParam(name = "lat") String lat)
    {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        System.out.println("USER:"+ currentPrincipalName);//erro aqui devolve NULL
        int typeValue=Integer.parseInt(value);
        Double longitude=Double.parseDouble(lon);
        Double latitude=Double.parseDouble(lat);
        registoRepository.save(new Registo(currentPrincipalName,typeValue, longitude,latitude, name));

        return "<a href=\"javascript:history.go(-2)\">Go Back</a>";

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
        String lines="";
        for (Registo registo : all) {
            lines +=("<tr>\n" +
                    "    <td>" + registo.getRegId() + "</td>\n" +
                    "    <td>" + registo.getLocalName() + "</td>\n" +
                    "    <td>" + registo.decodeStituation(registo.regType) + "</td>\n" +
                    "    <td>" + registo.getLongitude() + "</td>\n" +
                    "    <td>" + registo.getLatitude() + "</td>\n" +
                    "  </tr>");
        }
        //PrintWriter out = response.getWriter();
        return "<html>\n" +
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
                +("<body>\n")+
                ("<table>\n" +
                        "  <tr>\n" +
                        "    <th>RegistId</th>\n" +
                        "    <th>Place</th>\n" +
                        "    <th>Situation</th>\n" +
                        "    <th>Longitude</th>\n" +
                        "    <th>Latitude</th>\n" +
                        "  </tr>")+lines+
                "</table>\n" +
                "\n" +
                "</body>\n" +
                "</html>";

    }

    @RequestMapping(value="/userPanel/delete",method = RequestMethod.POST)
    public String deleteUser(@RequestParam(name = "idDelete") String id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        long idParsed=Long.parseLong(id);
        Iterable<Registo> userRegist = registoRepository.findByuserId(currentPrincipalName);
        for (Registo regist:userRegist) {
            if (regist.getRegId() == idParsed)
            {
                registoRepository.deleteById(idParsed);
                return "<a href=\"javascript:history.back()\">Go Back</a>";
            }
        }
        return "<script>window.alert('You can only erase your regists!') </script>";


    }



}
