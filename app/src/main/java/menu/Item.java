package menu;

import android.graphics.Bitmap;
import android.util.Log;

import java.util.Vector;

/**
 * @author Gunnar Sturla Ágústuson
 * @since 9.10.14
 * Klasinn sem heldur utan um hvern og einn rétt á matseðlinum og alla eiginleika hans
 */

public class Item {
    private int id,					// id á vörunni
            price,				// Verð vörunnar
            calories;			// Calories, ef = 0, þá ekki matur
    private String	name;            // nafn
            private String description;    // Textalýsing
            private String ingredients;    // innihaldsefni
            private String imghash;        // Einstakt hash fyrir þessar myndir,

    private String thumbBigUrl;
    private String comment;		// Comment sem notandi getur bætt við item þegar hann pantar
    private String thumbSmallUrl; // url fyrir small thumb
    // sem breytist ef myndirnar eru uppfærðar
    private Bitmap thumbsmall;

    private Bitmap thumbBig;


    public Item() {
//        thumbBigUrl = "";
        // Nýtt Item með engum upplýsingum, sem þarf svo að setja inn með item.setName("") og því
    }

    private Vector<String> filterable; // Vector sem heldur utan um síanlega hluti, eins og vegetarian, ofl.
    public Item(int id, int price, int cals, String name, String description, String ingredients, String imghash)
    {
        this.id 		= id;			// id á vörunni
        this.price 		= price;		// Verð vörunnar
        this.calories 	= cals;			// Calories, ef = 0, þá ekki matur
        this.name 		= name;			// nafn
        this.description= description;	// Textalýsing
        this.ingredients= ingredients;	// innihaldsefni
        this.imghash	= imghash;		// Einstakt hash fyrir þessar myndir,
        // sem breytist ef myndirnar eru uppfærðar
        comment = "";
        allergens =  new Vector<String>();
        filterable = new Vector<String>();
    }

    public Item(int id){
        this.id = id;
    }
    public void setId(int id) 					{ this.id = id; }
    public void setPrice(int price) 			{ this.price = price; 	}
    public void setCalories(int cal)			{ this.calories = cal; 	}
    public void setName(String name)			{ this.name = name; 		}
    public void setDescription(String desc)		{ this.description = desc; }
    public void setIngredients(String ingr)		{ this.ingredients = ingr; }
    public void setImghash(String imghash)		{ this.imghash = imghash;   }
    public void setComment(String c) 			{ this.comment = c; }
    private Vector<String> allergens;  // Vector sem heldur utan um ofnæmisvalda
    public void setThumbSmall(Bitmap thumbsmall) {        this.thumbsmall = thumbsmall;    }
    public void setThumbBig(Bitmap thumbbig) {        this.thumbBig = thumbbig;    }
    public String getThumbBigUrl() {
//        Log.i("returning thumg ", thumbBigUrl);
        return thumbBigUrl;    }

    public void setThumbBigUrl(String thumb) {
        Log.i("setting thumb to", thumb);
        thumbBigUrl = thumb;
    }

    // afritar basically annað item.
    // gagnlegt þegar kúnni setur nýjan rétt í
    // orderqueue
    public Item(Item i) {
        this.id 		= i.getId();	// id á vörunni
        this.price 		= i.getPrice();		// Verð vörunnar
        this.calories 	= i.getCalories();			// Calories, ef = 0, þá ekki matur
        this.name 		= i.getName();			// nafn
        this.description= i.getDescription();	// Textalýsing
        this.ingredients= i.getIngredients();	// innihaldsefni
        this.imghash	= i.getImghash();		// Einstakt hash fyrir þessar myndir,
        // sem breytist ef myndirnar eru uppfærðar
        allergens =  new Vector<String>();
        filterable = new Vector<String>();
    }

    // Bætir ofnæmisvaldi við, skilar true ef það tókst
    public boolean addAllergen(String s) {
        return allergens.add(s);
    }

    // Bætir filterable við, skilar true ef það tókst
    public boolean addFilterable(String f) {
        return filterable.add(f);
    }

    // Aðgerðir til að ná í upplýsingar úr þessum hlut.
    // Notkun, fyrir og eftir er frekar fyrirsjáanlegt.
    public int 	  getId() 			{ return id; 		}
    public int 	  getPrice() 		{ return price; 	}
	public String getPriceAsString(){ return String.format("%,.2f", price );}
    public int 	  getCalories()		{ return calories; 	}
    public String getName()			{ return name; 		}
    public String getDescription()	{ return description; }
    public String getIngredients()	{ return ingredients; }
    public String getImghash()		{ return imghash;	}
	public String getComment()		{ return comment;	}
    public Bitmap getThumbSmall()   { return this.thumbsmall;  }
    public Bitmap getThumbBig()		{ return this.thumbBig;    }

	public String getThumbSmallUrl() {
		return thumbSmallUrl;
	}

	public void setThumbSmallUrl(String thumbSmallUrl) {
		this.thumbSmallUrl = thumbSmallUrl;
	}

	private String thumbSmallUrl; // url fyrir small thumb

	public String getThumbBigUrl() {
		return thumbBigUrl;
	}

	public void setThumbBigUrl(String thumbBigUrl) {
		this.thumbBigUrl = thumbBigUrl;
	}


    public boolean hasAllergens() 	{ return !allergens.isEmpty(); }
    public boolean hasAllergen(String hasIt)
    {
        // Hér þarf að skrifa fall sem fer í gegnum
        // vectorinn og leitar að hasIt
        return false;
    }
    public Object[] getAllergens()	{ return allergens.toArray(); }

    public boolean hasFilterable()	{ return !filterable.isEmpty(); }
    public boolean hasFilterable(String hasIt)
    {
        // Hér þarf að skrifa fall sem fer í gegnum
        // vectorinn og leitar að hasIt
        return false;
    }
    public Object[] getFilterable() { return filterable.toArray(); }
}
