package com.awg.gpe.web.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.awg.gpe.web.controllers.HomeController;

/**
 * Controlador para las páginas de errores que implementa la interfaz {@link ErrorController}
 * <p>Por defecto, se usará la página con el error 500</p>
 * 
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
@RestController
public class GPEErrorController implements ErrorController {

    private static final String PATH = "/error";
    private static final String SERVER_ERROR = "error/error500.html";
    private static final String NOT_FOUND_ERROR = "error/error404.html";
    private static final String UNAUTHORIZED_ERROR = "error/error401.html";

    /**
     * Método que se ejecuta cuando se inserta la ruta /error
     * <p>Dependiendo del código de error del request, devuelve la página de error correspondiente</p>
     * 
     * @since 1.0
     * @param request
     *            Objeto {@link HttpServletRequest}
     * @param response
     *            Objeto {@link HttpServletResponse}
     * @return la página de error
     */
    @RequestMapping(value = GPEErrorController.PATH)
    public ModelAndView error(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        HomeController home = (HomeController) session.getAttribute("homeController");
        if (home != null) {
            home.setActualPage(home.getInitPage());
        }

        ModelAndView res = null;

        switch (response.getStatus()) {
            case 500:
                res = new ModelAndView(GPEErrorController.SERVER_ERROR);
                break;
            case 404:
                res = new ModelAndView(GPEErrorController.NOT_FOUND_ERROR);
                break;
            case 401:
                res = new ModelAndView(GPEErrorController.UNAUTHORIZED_ERROR);
                break;
            default:
                res = new ModelAndView(GPEErrorController.SERVER_ERROR);
                break;
        }

        return res;
    }

    // Getters and Setters ----------------------------------------------------
    /**
     * @see org.springframework.boot.autoconfigure.web.ErrorController#getErrorPath()
     */
    @Override
    public String getErrorPath() {
        return GPEErrorController.PATH;
    }
}
