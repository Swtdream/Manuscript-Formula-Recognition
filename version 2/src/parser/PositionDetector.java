package parser;

import graphic.MyRect;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PositionDetector {

	private List<Block> blocks = null;

	public PositionDetector() {
		blocks = new ArrayList<Block>();
	}

	public void setBlocks(List<MyRect> rects) {
		blocks.clear();
		for (MyRect rect : rects) {
			Block b = new Block(rect);
			this.blocks.add(b);
		}
		Collections.sort(blocks);
	}

	public String getResult() {
		String res = linearCombiner(blocks);
		return res;
	}

	// get latex String
	public String linearCombiner(List<Block> blocks) {
		for (int i = 0; i < blocks.size(); i++) {
			Block b = blocks.get(i);
			if (b.active) {
				if (b.canHaveScripts()) {
					List<Block> superScripts = new ArrayList<Block>();
					List<Block> subScripts = new ArrayList<Block>();
					for (int j = i + 1; j < blocks.size(); j++) {
						Block t = blocks.get(j);
						if (t.active) {
							if (b.hasSuperscript(t))
								superScripts.add(t);
							else if (b.hasSubscript(t))
								subScripts.add(t);
							else
								break;
						}
					}
					if (superScripts.size() > 0) {
						String superLatex = linearCombiner(superScripts);
						b.latex = b.latex + "^{" + superLatex + "}";
						b.value = 29;
					}
					if (subScripts.size() > 0) {
						String subLatex = linearCombiner(subScripts);
						b.latex = b.latex + "_{" + subLatex + "}";
						b.value = 29;
					}
				} else if (b.isBigSymbol()) {
					List<Block> upperBlocks = new ArrayList<Block>();
					List<Block> lowerBlocks = new ArrayList<Block>();
					for (int j = i - 1; j >= 0; j--) {
						Block t = blocks.get(j);
						if (b.hasUpper(t)) {
							upperBlocks.add(0, t);
						} else if (b.hasLower(t)) {
							lowerBlocks.add(0, t);
						} else
							break;
					}
					for (int j = i + 1; j < blocks.size(); j++) {
						Block t = blocks.get(j);
						if (b.hasUpper(t)) {
							upperBlocks.add(t);
						} else if (b.hasLower(t)) {
							lowerBlocks.add(t);
						} else
							break;
					}
					if (upperBlocks.size() > 0) {
						String upper = linearCombiner(upperBlocks);
						b.latex = b.latex + "^{" + upper + "}";
						b.value = 29;
					}
					if (lowerBlocks.size() > 0) {
						String lower = linearCombiner(lowerBlocks);
						b.latex = b.latex + "_{" + lower + "}";
						b.value = 29;
					}
				} else if (b.isFraction()) {
					List<Block> upperBlocks = new ArrayList<Block>();
					List<Block> lowerBlocks = new ArrayList<Block>();
					List<Block> likeUpperBlocks = new ArrayList<Block>();
					List<Block> likeLowerBlocks = new ArrayList<Block>();
					
					for(int j = i+1; j < blocks.size(); j++)
					{
						Block t = blocks.get(j);
						if(b.hasExactlyUpper(t))
						{
							if(t.isBigSymbol()) likeUpperBlocks.add(t);
							else upperBlocks.add(t);
						}
						else if(b.hasExactlyLower(t))
						{
							if(t.isBigSymbol()) likeLowerBlocks.add(t);
							else lowerBlocks.add(t);
						}
						else
							break;
					}
					for(Block t : likeUpperBlocks)
					{
						if(!t.hasExactlyRight(upperBlocks))
							upperBlocks = t.getLowerBlocks(upperBlocks);
						else
							upperBlocks.add(t);
					}
					for(Block t : likeLowerBlocks)
					{
						if(!t.hasExactlyRight(lowerBlocks))
							lowerBlocks = t.getUpperBlocks(lowerBlocks);
						else
							lowerBlocks.add(t);
					}
					if(upperBlocks.size() == 0 || lowerBlocks.size() == 0)
					{
						b.value = 25;
						b.latex = "-";
					}
					else
					{
						Collections.sort(upperBlocks);
						Collections.sort(lowerBlocks);
						String upper = linearCombiner(upperBlocks);
						String lower = linearCombiner(lowerBlocks);
						b.latex = b.latex + "{" + upper +"} {" + lower + "}";
						b.value = 29;
					}
				}
			}
		}
		String finalRes = "";
		for (int i = 0; i < blocks.size(); i++) {
			Block t = blocks.get(i);
			if (t.active) {
				t.active = false;
				if(!finalRes.equals("")) finalRes += " ";
				finalRes += t.latex;
			}
		}
		return finalRes;
	}

}
