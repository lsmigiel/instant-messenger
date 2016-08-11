/**
 * The only job of this class is to create objects for the three classes
 *  in the MVC architectural pattern.
 * @author Lukasz Smigielski
 *
 */
public class ClientMVC {
	public static void main(String[] args) throws Exception {
		ClientModel m = new ClientModel();
		ClientView v = new ClientView();
		ClientController c = new ClientController(m, v);
	}
}