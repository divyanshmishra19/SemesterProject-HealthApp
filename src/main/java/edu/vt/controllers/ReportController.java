package edu.vt.controllers;

import edu.vt.FacadeBeans.UserRecipeFacade;
import edu.vt.FacadeBeans.UserWorkoutFacade;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named("reportController")
@SessionScoped
public class ReportController implements Serializable {

    /*
    The @EJB annotation directs the EJB Container Manager to inject (store) the object reference of the
    UserRecipeFacade and UserWorkoutFacade bean into the instance variables 'userRecipeFacade' and 'userWorkoutFacade'
    after it is instantiated at runtime.
     */
    @EJB
    private UserRecipeFacade userRecipeFacade;

    @EJB
    private UserWorkoutFacade userWorkoutFacade;
}
