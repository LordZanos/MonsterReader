import java.util.ArrayList;

public class Creature {
	public String name;
	public String hitDice;
	public String size;
	public String type;
	public ArrayList<String> subtypes;
	public String groundSpeed;
	public String flySpeed;
	public String climbSpeed;
	public String armorClass;
	public String naturalArmor;
	public ArrayList<String> specialAttacks;
	public ArrayList<String> specialQualities;
	public String strength;
	public String dexterity;
	public String constitution;
	public String intelligence;
	public String wisdom;
	public String charisma;
	public ArrayList<String> feats;
	public String challengeRating;
	
	@Override
	public String toString() {
		return name + "\n" +
				"HD: " + hitDice + "\n" +
				"Type: " + type + "\n" +
				"Size: " + size + "\n" +
				"Str: " + strength + "\n" +
				"Dex: " + dexterity + "\n" +
				"Con: " + constitution + "\n" +
				"Int: " + intelligence + "\n" +
				"Wis: " + wisdom + "\n" +
				"Cha: " + charisma + "\n" +
				"NA: " + naturalArmor;
 	}
}
