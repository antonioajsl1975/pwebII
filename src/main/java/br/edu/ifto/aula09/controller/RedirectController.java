package br.edu.ifto.aula09.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RedirectController {

    @PostMapping("/saveRedirectAfterLogin")
    @ResponseBody
    public void saveRedirectAfterLogin(@RequestParam("url") String url, HttpSession session) {
        if (url != null && url.startsWith("/")) {  // Verifica se é uma URL relativa válida
            session.setAttribute("redirectAfterLogin", url);
        }
    }
}
