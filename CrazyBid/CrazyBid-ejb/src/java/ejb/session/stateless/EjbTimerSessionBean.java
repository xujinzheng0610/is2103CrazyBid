/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Stateless;

/**
 *
 * @author User
 */
@Stateless

public class EjbTimerSessionBean implements EjbTimerSessionBeanRemote, EjbTimerSessionBeanLocal {

    @EJB
    private AuctionListingEntityControllerLocal auctionListingEntityControllerLocal;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    @Schedule(hour = "*", minute = "*/1", info = "auctionListingEndDateCheckTimer")
    public void auctionListingEndDateCheckTimer()
    {
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        System.out.println("********** EjbTimerSession.auctionListingEndDateCheckTimer(): Timeout at " + timeStamp);
        
        auctionListingEntityControllerLocal.checkAuctionListingEndDate();
    }
}
