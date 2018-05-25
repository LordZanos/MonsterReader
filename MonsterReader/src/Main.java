import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		ArrayList<Creature> creatures = new ArrayList<Creature>();
		try {
			//creatures.addAll(new Book("D:/Desktop/D&D/manuals/D&D 3.5 - Monster Manual I [OEF].pdf", 9, 289).readMonsters());
			//creatures.addAll(new Book("D:/Desktop/D&D/manuals/D&D 3.5 - Monster Manual II [OEF].pdf", 9, 289).readMonsters());
			//creatures.addAll(new Book("D:/Desktop/D&D/manuals/D&D 3.5 - Monster Manual III [OEF].pdf", 9, 289).readMonsters());
			creatures.addAll(new Book("D:/Desktop/D&D/manuals/D&D 3.5 - Monster Manual IV [OEF].pdf", 9, 289).readMonsters());
			//creatures.addAll(new Book("D:/Desktop/D&D/manuals/D&D 3.5 - Monster Manual V [OEF].pdf", 9, 289).readMonsters());

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		for (Creature creature : creatures) {
			System.out.println(creature);
		}
		System.out.println(creatures.size());
	}
}
