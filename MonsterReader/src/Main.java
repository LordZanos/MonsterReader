import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Main {

	public static void main(String[] args) {
		ArrayList<Creature> creatures = new ArrayList<Creature>();
		try {

			creatures.addAll(new Book("D:/Desktop/D&D/manuals/D&D 3.5 - Draconomicon.pdf", 145, 198).readMonsters());
			creatures.addAll(new Book("D:/Desktop/D&D/manuals/D&D 3.5 - Lost Empires of Faerun.pdf", 160, 193).readMonsters());
			creatures.addAll(new Book("D:/Desktop/D&D/manuals/D&D 3.5 - Sandstorm - Mastering the Perils of Fire and Sand [OEF].pdf", 137, 198).readMonsters());
			creatures.addAll(new Book("D:/Desktop/D&D/manuals/D&D 3.5 - Lords of Madness - The Book of Aberrations [OEF].pdf", 135, 172).readMonsters());
			creatures.addAll(new Book("D:/Desktop/D&D/manuals/D&D 3.5 - Frostburn - Mastering the Perils of Ice and Snow [OEF].pdf", 113, 166).readMonsters());
			creatures.addAll(new Book("D:/Desktop/D&D/manuals/D&D 3.5 - Stormwrack - Mastering The Perils Of Wind And Wave [OEF].pdf", 135, 170).readMonsters());
			creatures.addAll(new Book("D:/Desktop/D&D/manuals/D&D 3.5 - Planar Handbook [OEF].pdf", 107, 134).readMonsters());
			creatures.addAll(new Book("D:/Desktop/D&D/manuals/D&D 3.5 - Libris Mortis - The Book of Undead [OEF].pdf", 81, 132).readMonsters());
			creatures.addAll(new Book("D:/Desktop/D&D/manuals/D&D 3.5 - Eberron Campaign Setting.pdf", 275, 306).readMonsters());
			creatures.addAll(new Book("D:/Desktop/D&D/manuals/D&D 3.5 - Book of Exalted Deeds [OEF].pdf", 157, 190).readMonsters());
			creatures.addAll(new Book("D:/Desktop/D&D/manuals/D&D 3.5 - Complete Arcane [OEF].pdf", 151, 162).readMonsters());
			creatures.addAll(new Book("D:/Desktop/D&D/manuals/D&D 3.5 - Expanded Psionics Handbook [OEF].pdf", 185, 218).readMonsters());
			creatures.addAll(new Book("D:/Desktop/D&D/manuals/D&D 3.5 - Races of Faerun [OEF].pdf", 174, 179).readMonsters());
			creatures.addAll(new Book("D:/Desktop/D&D/manuals/D&D 3.5 - Underdark.pdf", 78, 99).readMonsters());
			creatures.addAll(new Book("D:/Desktop/D&D/manuals/D&D 3.5 - Serpent Kingdoms.pdf", 62, 91).readMonsters());
			creatures.addAll(new Book("D:/Desktop/D&D/manuals/D&D 3.5 - Miniatures Handbook [OEF].pdf", 45, 72).readMonsters());
			creatures.addAll(new Book("D:/Desktop/D&D/manuals/D&D 3.5 - Tome of Magic [OEF].pdf", 80, 89).readMonsters());
			creatures.addAll(new Book("D:/Desktop/D&D/manuals/D&D 3.5 - Tome of Magic [OEF].pdf", 158, 166).readMonsters());
			creatures.addAll(new Book("D:/Desktop/D&D/manuals/D&D 3.5 - Tome of Magic [OEF].pdf", 264, 269).readMonsters());
			creatures.addAll(new Book("D:/Desktop/D&D/manuals/D&D 3.5 - Fiendish Codex I - Hordes of the Abyss [OEF].pdf", 27, 80).readMonsters());
			creatures.addAll(new Book("D:/Desktop/D&D/manuals/D&D 3.5 - Tome of Battle - Book of Nine Swords [OEF].pdf", 151, 156).readMonsters());
			creatures.addAll(new Book("D:/Desktop/D&D/manuals/D&D 3.5 - Fiendish Codex II - Tyrants of the Nine Hells [OEF].pdf", 107, 156).readMonsters());
			creatures.addAll(new Book("D:/Desktop/D&D/manuals/D&D 3.5 - Dungeonscape [OEF].pdf", 105, 116).readMonsters());
			creatures.addAll(new Book("D:/Desktop/D&D/manuals/D&D 3.5 - Monster Manual I [OEF].pdf", 9, 289).readMonsters());
			creatures.addAll(new Book("D:/Desktop/D&D/manuals/D&D 3.5 - Monster Manual II [OEF].pdf", 22, 221).readMonsters());
			creatures.addAll(new Book("D:/Desktop/D&D/manuals/D&D 3.5 - Monster Manual III [OEF].pdf", 8, 205).readMonsters());
			creatures.addAll(new Book("D:/Desktop/D&D/manuals/D&D 3.5 - Monster Manual IV [OEF].pdf", 8, 199).readMonsters());
			creatures.addAll(new Book("D:/Desktop/D&D/manuals/D&D 3.5 - Monster Manual V [OEF].pdf", 8, 202).readMonsters());
		} catch (Exception e) {
			e.printStackTrace();
		}
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet();
		int rowIndex = 0;
		XSSFRow row = sheet.createRow(rowIndex);
		row.createCell(0).setCellValue("Name");
		row.createCell(1).setCellValue("Type");
		row.createCell(2).setCellValue("Size");
		row.createCell(3).setCellValue("HD");
		row.createCell(4).setCellValue("Strength");
		row.createCell(5).setCellValue("Dexterity");
		row.createCell(6).setCellValue("Constitution");
		row.createCell(7).setCellValue("Intelligence");
		row.createCell(8).setCellValue("Wisdom");
		row.createCell(9).setCellValue("Charisma");
		row.createCell(10).setCellValue("NA");
		rowIndex++;
		for (Creature creature : creatures) {
			//System.out.println(creature);
			row = sheet.createRow(rowIndex);
			row.createCell(0).setCellValue(creature.name);
			row.createCell(1).setCellValue(creature.type);
			row.createCell(2).setCellValue(creature.size);
			row.createCell(3).setCellValue(creature.hitDice);
			row.createCell(4).setCellValue(creature.strength);
			row.createCell(5).setCellValue(creature.dexterity);
			row.createCell(6).setCellValue(creature.constitution);
			row.createCell(7).setCellValue(creature.intelligence);
			row.createCell(8).setCellValue(creature.wisdom);
			row.createCell(9).setCellValue(creature.charisma);
			row.createCell(10).setCellValue(creature.naturalArmor);
			rowIndex++;
		}
		FileOutputStream outFile;
		try {
			outFile = new FileOutputStream("output.xlsx");
			wb.write(outFile);
			wb.close();
			outFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(creatures.size());
	}
}
