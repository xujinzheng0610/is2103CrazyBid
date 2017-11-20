
package ws.premium;

import javax.xml.ws.WebFault;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.11-b150120.1832
 * Generated source version: 2.2
 * 
 */
@WebFault(name = "AuctionListingNotFoundException", targetNamespace = "http://premiumCustomer.ws/")
public class AuctionListingNotFoundException_Exception
    extends Exception
{

    /**
     * Java type that goes as soapenv:Fault detail element.
     * 
     */
    private AuctionListingNotFoundException faultInfo;

    /**
     * 
     * @param faultInfo
     * @param message
     */
    public AuctionListingNotFoundException_Exception(String message, AuctionListingNotFoundException faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @param faultInfo
     * @param cause
     * @param message
     */
    public AuctionListingNotFoundException_Exception(String message, AuctionListingNotFoundException faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @return
     *     returns fault bean: ws.premium.AuctionListingNotFoundException
     */
    public AuctionListingNotFoundException getFaultInfo() {
        return faultInfo;
    }

}
