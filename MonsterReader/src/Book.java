import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class Book {
	public ArrayList<Creature> creatures;
	private ArrayList<String> list;
    PDDocument document;
    File file;
	Pattern p = Pattern.compile("(?i)(Fine|Dimuntive|Tiny|Small|Medium|Medium-Size|Large|Huge|Gargantuan|Colossal) "
			+ "(Aberration|Animal|Construct|Deathless|Dragon|Elemental|Fey|Giant|Humanoid|Magical Beast|Monstrous Humanoid|Ooze|Outsider|Plant|Undead|Vermin)");

	public Book(String name, int startPage, int endPage) throws Exception {
		creatures = new ArrayList<Creature>();
		file = new File(name);
		document = PDDocument.load(file);
		PDFTextStripper stripper = new PDFTextStripper();
		stripper.setAddMoreFormatting(true);
		stripper.setStartPage(startPage);
		stripper.setEndPage(endPage);
		String[] lines = stripper.getText(document).split("\n");
	    list = new ArrayList<String>(Arrays.asList(lines));
	    list.removeAll(Collections.singleton("\n"));
	    list.removeAll(Collections.singleton("\r"));

	}
	
	public ArrayList<Creature> readMonsters() throws IOException {
		for(int currentIndex = 0; currentIndex < list.size(); currentIndex++) {
			int count = 0;
			Matcher m = p.matcher(list.get(currentIndex));
			ArrayList<String> sizeTypes = new ArrayList<String>();
			while(m.find()) {
				count++;
				sizeTypes.add(m.group(0));
			}
			if (count == 1) {
				int nameIndex = currentIndex - 1;
				String name = list.get(nameIndex).trim();
				while (name.contains("*")) {
					nameIndex--;
					name = list.get(nameIndex).trim();
				}
				creatures.add(createCreature(name, 0, currentIndex, sizeTypes.get(0)));
			}
			else if (count > 1) {
				m.reset();
				int nameIndex = currentIndex - 1;
				String name = list.get(nameIndex).trim();
				while (name.contains("*")) {
					nameIndex--;
					name = list.get(nameIndex).trim();
				}
				ArrayList<String> names = new ArrayList<String>();
				int stringSize = name.length() / count;
				int start = 0;
				int end = stringSize;
				while(end < name.length()) {
					names.add(name.substring(start,end));
					if(end + stringSize >= name.length()) {
						names.add(name.substring(end, name.length()));
					}
					start+= stringSize;
					end+= stringSize;
				}
				for (int x = 0; x < count; x++) {
					creatures.add(createCreature(names.get(x), x, currentIndex, sizeTypes.get(x)));
				}
			}
		}
		document.close();
		return creatures;
	}
	
	private Creature createCreature(String name, int x, int currentIndex, String sizeType) {
		Creature c = new Creature();
		c.name = name;
		c.hitDice = searchNextX(Pattern.compile("[0-9]{1,2}d[0-9]{1,2}"), currentIndex, 10, x).trim();
		if (c.hitDice.equals("ERROR FAIL")) {
			c.hitDice = c.hitDice = searchNextX(Pattern.compile("[0-9]{1,2} HD"), currentIndex, 10, x).trim().split(" ")[0];
		}
		else {
			c.hitDice = c.hitDice.split("d")[0];
		}
		c.size = sizeType.trim().split(" ")[0];
		c.type = sizeType.trim().split(" ")[1];
		c.strength = searchNextX(Pattern.compile("Str ([0-9]{1,2}|�)"), currentIndex, 50, x).trim().split(" ")[1];
		c.dexterity = searchNextX(Pattern.compile("Dex ([0-9]{1,2}|�)"), currentIndex, 50, x).trim().split(" ")[1];
		c.constitution = searchNextX(Pattern.compile("Con ([0-9]{1,2}|�)"), currentIndex, 50, x).trim().split(" ")[1];
		c.intelligence = searchNextX(Pattern.compile("Int ([0-9]{1,2}|�)"), currentIndex, 50, x).trim().split(" ")[1];
		c.wisdom = searchNextX(Pattern.compile("Wis ([0-9]{1,2}|�)"), currentIndex, 50, x).trim().split(" ")[1];
		c.charisma = searchNextX(Pattern.compile("Cha ([0-9]{1,2}|�)"), currentIndex, 50, x).trim().split(" ")[1];
		c.naturalArmor = searchNextX(Pattern.compile("(?i)\\+[0-9]{1,2} Natural"), currentIndex, 50, x).trim().split(" ")[0];
		return c;
	}
	
	private String searchNextX(Pattern p, int start, int limit, int group) {
		int currentIndex = start;
		String searchString = list.get(currentIndex);
		for(; currentIndex < start + limit; currentIndex++) {
			Matcher m = p.matcher(searchString);
			List<String> matches = new ArrayList<String>();
			while(m.find()) {
				matches.add(m.group(0));
			}
			if(matches.size() > group) {
				return matches.get(group);
			}
			searchString = searchString + list.get(currentIndex + 1).replaceAll("\r", " ").replaceAll(" +", " ");
		}
		return "ERROR FAIL";
	}
}