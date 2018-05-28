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
	    System.out.println(name);

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
		c.hitDice = searchHitDice(currentIndex, 20, x);
		c.size = sizeType.trim().split(" ")[0];
		c.type = sizeType.trim().split(" ")[1];
		c.strength = searchNextX(Pattern.compile("Str ([0-9]{1,2}|—)"), currentIndex, 50, x).trim().split(" ")[1];
		c.dexterity = searchNextX(Pattern.compile("Dex ([0-9]{1,2}|—)"), currentIndex, 50, x).trim().split(" ")[1];
		c.constitution = searchNextX(Pattern.compile("Con ([0-9]{1,2}|—)"), currentIndex, 50, x).trim().split(" ")[1];
		c.intelligence = searchNextX(Pattern.compile("Int ([0-9]{1,2}|—)"), currentIndex, 50, x).trim().split(" ")[1];
		c.wisdom = searchNextX(Pattern.compile("Wis ([0-9]{1,2}|—)"), currentIndex, 50, x).trim().split(" ")[1];
		c.charisma = searchNextX(Pattern.compile("Cha ([0-9]{1,2}|—)"), currentIndex, 50, x).trim().split(" ")[1];
		c.naturalArmor = searchNextX(Pattern.compile("(?i)\\+[0-9]{1,2} Natural"),currentIndex, 50, x).split(" ")[0];
		return c;
	}
	
	private String searchHitDice(int start, int limit, int group) {
		Pattern newFormat = Pattern.compile("[0-9]{1,2}d[0-9]{1,2}");
		Pattern oldFormat = Pattern.compile("[0-9]{1,2} HD");
		int currentIndex = start;
		String searchString = list.get(currentIndex);
		for(; currentIndex < start + limit; currentIndex++) {
			List<String> newMatches = new ArrayList<String>();
			List<String> oldMatches = new ArrayList<String>();
			Matcher newMatcher = newFormat.matcher(searchString);
			Matcher oldMatcher = oldFormat.matcher(searchString);
			while(newMatcher.find()) {
				newMatches.add(newMatcher.group(0));
			}
			while(oldMatcher.find()) {
				oldMatches.add(oldMatcher.group(0));
			}
			if(newMatches.size() >= 1 && oldMatches.size() >= 1) {
				return "FUCKY FUCKY";
			}
			else if (newMatches.size() > group) {
				return newMatches.get(group).trim().split("d")[0];
			}
			else if (oldMatches.size() > group) {
				return oldMatches.get(group).trim().split(" ")[0];
			}
			searchString = searchString + list.get(currentIndex + 1).replaceAll("\r", " ").replaceAll(" +", " ");
		}
		return "ERROR FAIL";
	}
	
	//needs work, difficulty parsing correct number when +X Natural is split across two lines in multi-monster tables
	/*private String searchNatArmor(int start, int limit, int group) {
		Pattern standard = Pattern.compile("(?i)\\+[0-9]{1,2} Natural");
		Pattern split = Pattern.compile("(?i)\\+[0-9]{1,2} ((?!Natural)|(?!Dex))");
		int currentIndex = start;
		String searchString = list.get(currentIndex);
		for(; currentIndex < start + limit; currentIndex++) {
			List<String> standardMatches = new ArrayList<String>();
			List<String> splitMatches = new ArrayList<String>();
			Matcher standardMatcher = standard.matcher(searchString);
			Matcher splitMatcher = split.matcher(searchString);
			while(standardMatcher.find()) {
				standardMatches.add(standardMatcher.group(0));
			}
			while(splitMatcher.find()) {
				if(list.get(currentIndex + 1).contains("natural")) {
					splitMatches.add(splitMatcher.group(0));
				}
			}
			if (standardMatches.size() > group) {
				return standardMatches.get(group).trim().split(" ")[0];
			}
			else if (splitMatches.size() > group) {
				return splitMatches.get(group).trim();
			}
			searchString = list.get(currentIndex + 1).replaceAll(" +", " ");
		}
		return "ERROR FAIL";
	}*/
	
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
			searchString = searchString + list.get(currentIndex + 1).replaceAll("\r", " ").replaceAll(" +", " ").replaceAll("-", "");
		}
		return "ERROR FAIL";
	}
}
