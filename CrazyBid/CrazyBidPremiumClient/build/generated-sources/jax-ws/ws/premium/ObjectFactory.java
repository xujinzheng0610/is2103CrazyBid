
package ws.premium;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the ws.premium package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _AuctionListingNotFoundException_QNAME = new QName("http://premiumCustomer.ws/", "AuctionListingNotFoundException");
    private final static QName _CustomerNotFoundException_QNAME = new QName("http://premiumCustomer.ws/", "CustomerNotFoundException");
    private final static QName _InvalidLoginCredentialException_QNAME = new QName("http://premiumCustomer.ws/", "InvalidLoginCredentialException");
    private final static QName _CustomerLogin_QNAME = new QName("http://premiumCustomer.ws/", "customerLogin");
    private final static QName _CustomerLoginResponse_QNAME = new QName("http://premiumCustomer.ws/", "customerLoginResponse");
    private final static QName _NewConfigureSniping_QNAME = new QName("http://premiumCustomer.ws/", "newConfigureSniping");
    private final static QName _NewConfigureSnipingResponse_QNAME = new QName("http://premiumCustomer.ws/", "newConfigureSnipingResponse");
    private final static QName _RetrieveAllAuctionListings_QNAME = new QName("http://premiumCustomer.ws/", "retrieveAllAuctionListings");
    private final static QName _RetrieveAllAuctionListingsResponse_QNAME = new QName("http://premiumCustomer.ws/", "retrieveAllAuctionListingsResponse");
    private final static QName _RetrieveAuctionListingById_QNAME = new QName("http://premiumCustomer.ws/", "retrieveAuctionListingById");
    private final static QName _RetrieveAuctionListingByIdResponse_QNAME = new QName("http://premiumCustomer.ws/", "retrieveAuctionListingByIdResponse");
    private final static QName _RetrieveCustomerByUsername_QNAME = new QName("http://premiumCustomer.ws/", "retrieveCustomerByUsername");
    private final static QName _RetrieveCustomerByUsernameResponse_QNAME = new QName("http://premiumCustomer.ws/", "retrieveCustomerByUsernameResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: ws.premium
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link AuctionListingNotFoundException }
     * 
     */
    public AuctionListingNotFoundException createAuctionListingNotFoundException() {
        return new AuctionListingNotFoundException();
    }

    /**
     * Create an instance of {@link CustomerNotFoundException }
     * 
     */
    public CustomerNotFoundException createCustomerNotFoundException() {
        return new CustomerNotFoundException();
    }

    /**
     * Create an instance of {@link InvalidLoginCredentialException }
     * 
     */
    public InvalidLoginCredentialException createInvalidLoginCredentialException() {
        return new InvalidLoginCredentialException();
    }

    /**
     * Create an instance of {@link CustomerLogin }
     * 
     */
    public CustomerLogin createCustomerLogin() {
        return new CustomerLogin();
    }

    /**
     * Create an instance of {@link CustomerLoginResponse }
     * 
     */
    public CustomerLoginResponse createCustomerLoginResponse() {
        return new CustomerLoginResponse();
    }

    /**
     * Create an instance of {@link NewConfigureSniping }
     * 
     */
    public NewConfigureSniping createNewConfigureSniping() {
        return new NewConfigureSniping();
    }

    /**
     * Create an instance of {@link NewConfigureSnipingResponse }
     * 
     */
    public NewConfigureSnipingResponse createNewConfigureSnipingResponse() {
        return new NewConfigureSnipingResponse();
    }

    /**
     * Create an instance of {@link RetrieveAllAuctionListings }
     * 
     */
    public RetrieveAllAuctionListings createRetrieveAllAuctionListings() {
        return new RetrieveAllAuctionListings();
    }

    /**
     * Create an instance of {@link RetrieveAllAuctionListingsResponse }
     * 
     */
    public RetrieveAllAuctionListingsResponse createRetrieveAllAuctionListingsResponse() {
        return new RetrieveAllAuctionListingsResponse();
    }

    /**
     * Create an instance of {@link RetrieveAuctionListingById }
     * 
     */
    public RetrieveAuctionListingById createRetrieveAuctionListingById() {
        return new RetrieveAuctionListingById();
    }

    /**
     * Create an instance of {@link RetrieveAuctionListingByIdResponse }
     * 
     */
    public RetrieveAuctionListingByIdResponse createRetrieveAuctionListingByIdResponse() {
        return new RetrieveAuctionListingByIdResponse();
    }

    /**
     * Create an instance of {@link RetrieveCustomerByUsername }
     * 
     */
    public RetrieveCustomerByUsername createRetrieveCustomerByUsername() {
        return new RetrieveCustomerByUsername();
    }

    /**
     * Create an instance of {@link RetrieveCustomerByUsernameResponse }
     * 
     */
    public RetrieveCustomerByUsernameResponse createRetrieveCustomerByUsernameResponse() {
        return new RetrieveCustomerByUsernameResponse();
    }

    /**
     * Create an instance of {@link Customer }
     * 
     */
    public Customer createCustomer() {
        return new Customer();
    }

    /**
     * Create an instance of {@link Address }
     * 
     */
    public Address createAddress() {
        return new Address();
    }

    /**
     * Create an instance of {@link AuctionListing }
     * 
     */
    public AuctionListing createAuctionListing() {
        return new AuctionListing();
    }

    /**
     * Create an instance of {@link Bid }
     * 
     */
    public Bid createBid() {
        return new Bid();
    }

    /**
     * Create an instance of {@link TopUpTransaction }
     * 
     */
    public TopUpTransaction createTopUpTransaction() {
        return new TopUpTransaction();
    }

    /**
     * Create an instance of {@link CreditPackage }
     * 
     */
    public CreditPackage createCreditPackage() {
        return new CreditPackage();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AuctionListingNotFoundException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://premiumCustomer.ws/", name = "AuctionListingNotFoundException")
    public JAXBElement<AuctionListingNotFoundException> createAuctionListingNotFoundException(AuctionListingNotFoundException value) {
        return new JAXBElement<AuctionListingNotFoundException>(_AuctionListingNotFoundException_QNAME, AuctionListingNotFoundException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CustomerNotFoundException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://premiumCustomer.ws/", name = "CustomerNotFoundException")
    public JAXBElement<CustomerNotFoundException> createCustomerNotFoundException(CustomerNotFoundException value) {
        return new JAXBElement<CustomerNotFoundException>(_CustomerNotFoundException_QNAME, CustomerNotFoundException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InvalidLoginCredentialException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://premiumCustomer.ws/", name = "InvalidLoginCredentialException")
    public JAXBElement<InvalidLoginCredentialException> createInvalidLoginCredentialException(InvalidLoginCredentialException value) {
        return new JAXBElement<InvalidLoginCredentialException>(_InvalidLoginCredentialException_QNAME, InvalidLoginCredentialException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CustomerLogin }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://premiumCustomer.ws/", name = "customerLogin")
    public JAXBElement<CustomerLogin> createCustomerLogin(CustomerLogin value) {
        return new JAXBElement<CustomerLogin>(_CustomerLogin_QNAME, CustomerLogin.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CustomerLoginResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://premiumCustomer.ws/", name = "customerLoginResponse")
    public JAXBElement<CustomerLoginResponse> createCustomerLoginResponse(CustomerLoginResponse value) {
        return new JAXBElement<CustomerLoginResponse>(_CustomerLoginResponse_QNAME, CustomerLoginResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NewConfigureSniping }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://premiumCustomer.ws/", name = "newConfigureSniping")
    public JAXBElement<NewConfigureSniping> createNewConfigureSniping(NewConfigureSniping value) {
        return new JAXBElement<NewConfigureSniping>(_NewConfigureSniping_QNAME, NewConfigureSniping.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NewConfigureSnipingResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://premiumCustomer.ws/", name = "newConfigureSnipingResponse")
    public JAXBElement<NewConfigureSnipingResponse> createNewConfigureSnipingResponse(NewConfigureSnipingResponse value) {
        return new JAXBElement<NewConfigureSnipingResponse>(_NewConfigureSnipingResponse_QNAME, NewConfigureSnipingResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RetrieveAllAuctionListings }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://premiumCustomer.ws/", name = "retrieveAllAuctionListings")
    public JAXBElement<RetrieveAllAuctionListings> createRetrieveAllAuctionListings(RetrieveAllAuctionListings value) {
        return new JAXBElement<RetrieveAllAuctionListings>(_RetrieveAllAuctionListings_QNAME, RetrieveAllAuctionListings.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RetrieveAllAuctionListingsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://premiumCustomer.ws/", name = "retrieveAllAuctionListingsResponse")
    public JAXBElement<RetrieveAllAuctionListingsResponse> createRetrieveAllAuctionListingsResponse(RetrieveAllAuctionListingsResponse value) {
        return new JAXBElement<RetrieveAllAuctionListingsResponse>(_RetrieveAllAuctionListingsResponse_QNAME, RetrieveAllAuctionListingsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RetrieveAuctionListingById }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://premiumCustomer.ws/", name = "retrieveAuctionListingById")
    public JAXBElement<RetrieveAuctionListingById> createRetrieveAuctionListingById(RetrieveAuctionListingById value) {
        return new JAXBElement<RetrieveAuctionListingById>(_RetrieveAuctionListingById_QNAME, RetrieveAuctionListingById.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RetrieveAuctionListingByIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://premiumCustomer.ws/", name = "retrieveAuctionListingByIdResponse")
    public JAXBElement<RetrieveAuctionListingByIdResponse> createRetrieveAuctionListingByIdResponse(RetrieveAuctionListingByIdResponse value) {
        return new JAXBElement<RetrieveAuctionListingByIdResponse>(_RetrieveAuctionListingByIdResponse_QNAME, RetrieveAuctionListingByIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RetrieveCustomerByUsername }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://premiumCustomer.ws/", name = "retrieveCustomerByUsername")
    public JAXBElement<RetrieveCustomerByUsername> createRetrieveCustomerByUsername(RetrieveCustomerByUsername value) {
        return new JAXBElement<RetrieveCustomerByUsername>(_RetrieveCustomerByUsername_QNAME, RetrieveCustomerByUsername.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RetrieveCustomerByUsernameResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://premiumCustomer.ws/", name = "retrieveCustomerByUsernameResponse")
    public JAXBElement<RetrieveCustomerByUsernameResponse> createRetrieveCustomerByUsernameResponse(RetrieveCustomerByUsernameResponse value) {
        return new JAXBElement<RetrieveCustomerByUsernameResponse>(_RetrieveCustomerByUsernameResponse_QNAME, RetrieveCustomerByUsernameResponse.class, null, value);
    }

}
