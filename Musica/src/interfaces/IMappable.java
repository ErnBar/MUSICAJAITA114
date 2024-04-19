package interfaces;

import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public interface IMappable {

    default Map<String,String> tomap(){
        Map<String,String>ris=new HashMap<>();
        String keyProp="";
        String valoreProp="";
        boolean check=false;
        for (Method m:this.getClass().getMethods()){
            if (m.getName().startsWith("get")&& !"getClass".equals(m.getName())){
                keyProp=m.getName().substring(3);
                check=true;
            } else if (m.getName().startsWith("is")) {
                keyProp=m.getName().substring(2);
                check=true;
            }
            if (check) {
                keyProp=Character.toLowerCase(keyProp.charAt(0))+keyProp.substring(1);
                try {
                    valoreProp=String.valueOf(m.invoke(this));
                } catch (Exception e) {
                    e.printStackTrace();
                    System.err.println("Errore nel to map di "+this.getClass().getName());
                }
                ris.put(keyProp,valoreProp);
                check=false;
            }
        }
        return ris;
    }


    default void fromMap(Map<String,String>params){
        String nomeProp;
        String valoreProp;
        for (Method m:this.getClass().getMethods()){
            if (m.getName().startsWith("set")){
                nomeProp=m.getName().substring(3);
                nomeProp=Character.toLowerCase(nomeProp.charAt(0))+nomeProp.substring(1);
                if (params.containsKey(nomeProp)){
                    valoreProp=params.get(nomeProp);
                    String tipo=m.getParameters()[0].getType().getSimpleName();
                    try {
                        switch (tipo.toLowerCase()){
                            case "string":
                                m.invoke(this,valoreProp);
                                break;
                            case "double":
                                m.invoke(this,Double.parseDouble(valoreProp));
                                break;
                            case "int":
                                m.invoke(this,Integer.parseInt(valoreProp));
                                break;
                            case "date":
                                String dateString = params.get(nomeProp);
                                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                                java.util.Date date = null;
                                try {
                                    date = formatter.parse(dateString);
                                } catch (ParseException e) {
                                    System.out.println("Errore durante il parsing della data: " + e.getMessage());
                                }
                                m.invoke(this, date);
                                break;
                                //OPPURE
//                            case "date":
//                                m.invoke(this, Date.valueOf(valoreProp));
//                                break;
                            default:
                                System.err.println("Attenzione non ho riconosciuto il tipo! "+tipo);
                                break;
                        }
                    }catch (Exception e){
                        System.err.println("Errore nel metodo fromMap");
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
