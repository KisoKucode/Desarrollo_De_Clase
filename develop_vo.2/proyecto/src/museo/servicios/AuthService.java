package museo.servicios;

import java.util.HashMap;
import java.util.Map;

public class AuthService {
    private final Map<String,String> users = new HashMap<>(); // user->role
    public AuthService(){
        users.put("director","DIRECTOR");
        users.put("encargado","ENCARGADO");
        users.put("restaurador","RESTAURADOR");
        users.put("visitante","VISITANTE");
    }
    public String autenticar(String user, String pass){
        // demo: password ignored
        return users.get(user);
    }
}
