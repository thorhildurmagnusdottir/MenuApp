package menu;

import android.util.Log;

/**
 * Created by Dagný on 30.10.2014.
 */
public class Order {

    private static SubMenu order = new SubMenu();

    // Ný vara pöntuð

    public static boolean addOrder(Item item) {
        return order.addItem(item);
    }

    public static Item remove(int itemNo) {
        return order.removeItem(itemNo);
    }

	public static Item get(int i) { return order.get(i); }

    // Pöntunin sótt
    public static SubMenu getOrder() {
        return order;
    }

	public static int getTotal() {
		int sum = 0;
		for(int i = 0; i < order.size(); ++i)
			sum += order.get(i).getPrice();

		return sum;
	}


	public static boolean pay() {
		//TODO Herma einhverja borgunarvirkni.
		Log.i("Order:", "Borgaðu, helvítið þitt! Ok, gott, takk.");
		return Order.clear();
	}

	public static boolean clear() {
		order = new SubMenu();
		return true;
	}

    public static boolean isEmpty() {
        return order.isEmpty();
    }
    // skilar inn í OrderListAdapter
    public static int size() {
		if(order != null) {
			return order.size();
		} else {
			return 0;
		}
    }
}


