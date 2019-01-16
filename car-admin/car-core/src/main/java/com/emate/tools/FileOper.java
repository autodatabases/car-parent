package com.emate.tools;

import java.io.File;

import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class FileOper {
	public static void main(String[] args) throws BadHanyuPinyinOutputFormatCombination {
		String dirName = "C:\\Users\\likk\\Desktop\\auto\\peijian_image\\jiyou";
		File dir = new File(dirName);
		PinyinTool p = new PinyinTool();
		for(File f:dir.listFiles()){
			String zhongwen = f.getName();
			zhongwen = zhongwen.replaceAll("\\(", "").replaceAll("\\)", "").replaceAll(" " , "");
			zhongwen = p.toPinYin(zhongwen);
			System.out.println(f.renameTo(new File(dirName,zhongwen)));
		}
	}
}
