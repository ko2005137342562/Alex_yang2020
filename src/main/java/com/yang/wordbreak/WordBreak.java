package com.yang.wordbreak;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.beanutils.BeanUtils;

/**
 * �ֵ������
 * @author ����
 * @since 2020-3-8
 */
public class WordBreak {
	
	private List<Word> words = null;
	
	private Map<Integer,String> resultWordMap = new HashMap<Integer,String>();

	private Set<String> resultDictionary = new HashSet<String>();
	
	/**
	 * ���췽������ʼ���ֵ䣬����û��ֵ䲻Ϊ����ʹ���û��ֵ䣬����ʹ��Ĭ���ֵ�
	 * �ֵ䰴���ַ����ĳ����ɴ�С���򣬲����õ��ʵ��±꣬������ѯ����ʹ��
	 * @param dictionary
	 */
	public WordBreak(Dictionary dictionary) {
		//��ֹ�Ͼ���������û����뵥�ʣ���Ҫcopyһ��
		if(Dictionary.USER_WORDS.size() == 0) {
			words = this.copyWordList(Dictionary.DEF_WORDS);
		} else {
			words = this.copyWordList(Dictionary.USER_WORDS);
		}
		words.sort(Comparator.comparing(Word::getLength).reversed());
		for(int i=0;i<words.size();i++) {
			words.get(i).setIndex(i+1);
		}
	}
	
	/**
	 * ������ľ��Ӹ����ֵ���жϾ�
	 * ��һ������˳�����ɵ���������ĳ�����ʵķ�֧��
	 * �ڶ������ҳ����ܵĵ������
	 * �����������±��ҳ�����ƴ�Ӳ�����
	 * @param inputSentence
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws NoSuchMethodException 
	 * @throws InstantiationException 
	 */
	public List<String> breakWord(String inputSentence) {
		Word sentenceWord = new Word();
		sentenceWord.setContent(inputSentence);
		sentenceWord.setIndex(0);
		sentenceWord.setLength(inputSentence.length());
		
		//generate���޸ĵ������ݣ���Ҫcopyһ��
		List<Word> copyWords = copyWordList(this.words);
		this.generate(sentenceWord, sentenceWord.getLength(), copyWords);
		
		if(resultWordMap.size() == 0) {
			System.out.println("ȱ�ٵ��ʣ��޷��Ͼ䣡"); 
			return Arrays.asList(inputSentence);
		}
		String changedContent = sentenceWord.getContent();
		Set<String> set = new HashSet<String>();
		set.add(changedContent);
		resultDictionary.add(changedContent);
		put(set);
		
		List<String> breakSentence = new ArrayList<String>();
		resultDictionary.forEach(e -> {
			StringBuffer sb = new StringBuffer();
			pickContent(e, sb);
			breakSentence.add(sb.toString().substring(0, sb.length()-1));
		});
		return breakSentence;
	} 
	
	/**
	 * �ݹ��ѯ���ܳ��ֵ����
	 * @param dictionarySet
	 */
	private void put(Set<String> dictionarySet) {
		Set<String> newDictionarySet = new HashSet<String>();
		dictionarySet.forEach(e -> {
			String arayStr = e.substring(1, e.length()-1);
			String[] array = arayStr.split("##");
			for(int i=0;i<array.length;i++) {
				String value = resultWordMap.get(Integer.valueOf(array[i]));
				if(value != null) {
					String newStr = e.replaceAll("#"+array[i]+"#", value);
					resultDictionary.add(newStr);
					newDictionarySet.add(newStr);
				}
			}
		});
		if(newDictionarySet.size() == 0) {
			return;
		} else {
			put(newDictionarySet);
		}
	}
	
	/**
	 * ѭ���ݹ��ѯ�������
	 * @param sentence
	 * @param length
	 * @param words
	 */
	private void generate(Word sentence, int length, List<Word> words) {	
		Word wordItem = null;
		
		for(int i=0;i<words.size();i++) {
			wordItem = words.get(i);
			if(sentence.getContent().contains(wordItem.getContent()) && 
					!sentence.getContent().equals(wordItem.getContent())) {
				String newContent = sentence.getContent().replaceAll(wordItem.getContent(), 
						"#" + String.valueOf(wordItem.getIndex()) + "#");
				sentence.setContent(newContent);
				generate(wordItem, wordItem.getLength(), words);
			}
		}
		
		//�����Ҷ�����������like��������ҵ�i����Ҫ����
		String content = sentence.getContent();
		if(!isContainLetter(content)) {
			resultWordMap.put(sentence.getIndex(), content);
		}
	}
	
	/**
	 * �ж��ַ������Ƿ�����ַ�
	 * @param str
	 * @return
	 */
	private boolean isContainLetter(String str) {
		for(int i=0;i<str.length();i++) {
			if(Character.isLetter(str.charAt(i))){ //��char��װ���е��ж���ĸ�ķ����ж�ÿһ���ַ�
				return true;
			}
		}
		return false;
	}
	
	/**
	 * ����index��ȡ����
	 * @param str
	 * @return
	 */
	private void pickContent(String str, StringBuffer sb) {
		String arayStr = str.substring(1, str.length()-1);
		String[] array = arayStr.split("##");
		for(int i=0;i<array.length;i++) {
			String indexStr = array[i];
			List<Word> filtWord = words.stream().filter(e -> String.valueOf(e.getIndex()).equals(indexStr)).collect(Collectors.toList());
			if(filtWord != null && filtWord.size() > 0) {
				sb.append(filtWord.get(0).getContent()).append(" ");
			}
		}
	}
	
	/**
	 * �����ֵ�
	 * @param words
	 * @return
	 */
	private List<Word> copyWordList(Collection<Word> words) {
		List<Word> newWords = new ArrayList<Word>();
		words.forEach(e -> {
			Word word = new Word();
			try {
				BeanUtils.copyProperties(word, e);
			} catch (IllegalAccessException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (InvocationTargetException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			newWords.add(word);
		});
		return newWords;
	}
	
	/**
	 * 
	 * @param args
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws NoSuchMethodException 
	 * @throws InstantiationException 
	 */
	
	public static void main(String[] args) {
		Dictionary dictionary = new Dictionary();
		dictionary.addCustomized("i,like,sam,sung,samsung,mobile,ice,cream,man go");
		
		WordBreak dop = new WordBreak(dictionary);

		List<String> list = dop.breakWord("ilikesamsungmobile");
		System.out.println(list);
	}
	
	
}
