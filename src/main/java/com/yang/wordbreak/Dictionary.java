package com.yang.wordbreak;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Dictionary {

	/** 默认字典 **/
	public static final Set<Word> DEF_WORDS = new HashSet<Word>();
	
	/** 用户自定义字典 **/
	public static final Set<Word> USER_WORDS = new HashSet<Word>();
	
	public static final String DEFAULT_DICTIONARY = "default.dictionary";
	
	public static final String DEFAULT = "default";
	
	public static final String CUSTOMIZED = "customized";
	
	public Dictionary() {
		String defaultDict = PropertiesUtils.getPropertyValues(DEFAULT_DICTIONARY);
		add(defaultDict, DEFAULT);
	}
	
	/**
	 * 用户增加字典
	 * @param dictionaryStr
	 */
	public void addCustomized(String dictionaryStr) {
		this.add(dictionaryStr, CUSTOMIZED);
	}
	
	public void removeCustomized() {
		USER_WORDS.clear();
	}
	
	private void add(String dictionary, String type) {
		if(null != dictionary && !"".equals(dictionary)) {
			Set<Word> set = null;
			if(type.equals(DEFAULT)) {
				set = DEF_WORDS;
			} else if (type.equals(CUSTOMIZED)) {
				set = USER_WORDS;
			}
			String[] wordStrs = dictionary.split(",");
			for(String wordStr : wordStrs) {
				set.add(buildWord(wordStr));
			}
		}
	}
	
	private Word buildWord(String wordStr) {
		Word word = new Word();
		word.setContent(wordStr);
		word.setLength(wordStr.length());
		return word;
	}
}
