package com.example.securingweb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.List;


@RestController
public class RegistControler extends HttpServlet {

    private RegistoRepository registoRepository;
    private UtilizadorRepository utilizadorRepository;

    @Autowired
    public RegistControler(RegistoRepository registoRepository,UtilizadorRepository utilizadorRepository){
        this.registoRepository=registoRepository;
        this.utilizadorRepository=utilizadorRepository;
    }

    @GetMapping("/locals")
    public String getLocals() throws IOException {

        Iterable<Registo> example= registoRepository.findDistinctByRegIdAndLocalNameAndLongitudeAndLatitudeAndRegTypeAndCountAndRegType(, , , , )
        for (Registo votes : example)
        {
            System.out.println("Test "+ votes.localName);
        }
        System.out.println("AQUIII  " +example);
       return makeTable();
    }

    public String makeTable() throws IOException {
        //response.setContentType("text/html");
        Iterable<Registo> all = registoRepository.findAll();
        String lines="";
        for (Registo registo : all) {
            lines +=("<tr>\n" +
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
