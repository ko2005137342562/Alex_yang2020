package com.yang.test;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import com.yang.wordbreak.Dictionary;
import com.yang.wordbreak.WordBreak;

import junit.framework.Assert;

public class WordBreakTest {

	@Before
	public void setUp() throws Exception {
	}

	/**
	 * ����Ĭ���ֵ�Ͼ䣬�������
	 */
	@Test
	public void test1() {
		Dictionary dictionary = new Dictionary();
		WordBreak wb = new WordBreak(dictionary);
		
		String sentence = "ilikesamsungmobile";
		List<String> words = wb.breakWord(sentence);
		Assert.assertEquals(2, words.size());
		
		int count = 0;
		for(String str : words) {
			if(str.equals("i like samsung mobile") ||
					str.equals("i like sam sung mobile")) {
				count++;
			}
		}
		Assert.assertEquals(2, count);
	}
	
	/**
	 * �����û��ֵ�Ͼ䣨һ����������������еĵ��ʣ�
	 */
	@Test
	public void test2() {
		Dictionary dictionary = new Dictionary();
		dictionary.addCustomized("aaa");
		
		String sentence = "ilikesamsungmobile";
		WordBreak wb = new WordBreak(dictionary);
		List<String> words = wb.breakWord(sentence);
		
		Assert.assertEquals(1, words.size());
		Assert.assertEquals(sentence, words.get(0));
		
		dictionary.removeCustomized();
		
	}
	
	
	/**
	 * �����û��ֵ�Ͼ䣨ֻ��һ��������������еĵ��ʣ�
	 */
	@Test
	public void test3() {
		Dictionary dictionary = new Dictionary();
		dictionary.addCustomized("mobile");
		
		String sentence = "ilikesamsungmobile";
		WordBreak wb = new WordBreak(dictionary);
		List<String> words = wb.breakWord(sentence);
		
		Assert.assertEquals(1, words.size());
		Assert.assertEquals(sentence, words.get(0));
		
		dictionary.removeCustomized();
		
	}
	
	/**
	 * �����û��ֵ�Ͼ䣨���ڶ����������еĵ��ʣ�����ȫ�����У�
	 */
	@Test
	public void test4() {
		Dictionary dictionary = new Dictionary();
		dictionary.addCustomized("mobile,samsung,like");
		
		String sentence = "ilikesamsungmobile";
		WordBreak wb = new WordBreak(dictionary);
		List<String> words = wb.breakWord(sentence);
		
		Assert.assertEquals(1, words.size());
		Assert.assertEquals(sentence, words.get(0));
		
		dictionary.removeCustomized();
		
	}
	
	/**
	 * �����û��ֵ�Ͼ䣨������������е�ȫ�����ʣ�
	 */
	@Test
	public void test5() {
		Dictionary dictionary = new Dictionary();
		dictionary.addCustomized("i,like,sam,sung,mobile");
		
		String sentence = "ilikesamsungmobile";
		WordBreak wb = new WordBreak(dictionary);
		List<String> words = wb.breakWord(sentence);
		
		Assert.assertEquals(1, words.size());
		Assert.assertEquals("i like sam sung mobile", words.get(0));
		
		dictionary.removeCustomized();
		
	}

	/**
	 * �����û��ֵ�Ͼ䣨������������е�ȫ�����ʣ�
	 */
	@Test
	public void test6() {
		Dictionary dictionary = new Dictionary();
		dictionary.addCustomized("i,like,samsung,mobile");
		
		String sentence = "ilikesamsungmobile";
		WordBreak wb = new WordBreak(dictionary);
		List<String> words = wb.breakWord(sentence);
		
		Assert.assertEquals(1, words.size());
		Assert.assertEquals("i like samsung mobile", words.get(0));
		
		dictionary.removeCustomized();
		
	}
	
	/**
	 * �����û��ֵ�Ͼ䣨���ڶ����������е�ȫ�����ʣ�
	 */
	@Test
	public void test7() {
		Dictionary dictionary = new Dictionary();
		dictionary.addCustomized("i,like,sam,sung,samsung,mobile");
		
		String sentence = "ilikesamsungmobile";
		WordBreak wb = new WordBreak(dictionary);
		List<String> words = wb.breakWord(sentence);
		
		Assert.assertEquals(2, words.size());
		
		int count = 0;
		for(String str : words) {
			if(str.equals("i like samsung mobile") ||
					str.equals("i like sam sung mobile")) {
				count++;
			}
		}
		Assert.assertEquals(2, count);
		
		dictionary.removeCustomized();
		
	}
	
	/**
	 * �����û��ֵ�Ͼ䣨���ڶ����������е�ȫ�����ʣ�
	 */
	@Test
	public void test8() {
		Dictionary dictionary = new Dictionary();
		dictionary.addCustomized("i,like,sam,sung,samsung,mobile,mon,go");
		
		String sentence = "ilikesamsungmobile";
		WordBreak wb = new WordBreak(dictionary);
		List<String> words = wb.breakWord(sentence);
		
		Assert.assertEquals(2, words.size());
		
		int count = 0;
		for(String str : words) {
			if(str.equals("i like samsung mobile") ||
					str.equals("i like sam sung mobile")) {
				count++;
			}
		}
		Assert.assertEquals(2, count);
		
		dictionary.removeCustomized();
		
	}
	
	/**
	 * �����û��ֵ�Ͼ䣨���ڶ����������е�ȫ�����ʣ�
	 */
	@Test
	public void test9() {
		Dictionary dictionary = new Dictionary();
		dictionary.addCustomized("i,like,sam,sung,samsung,mobile,mon,go,mob,ile");
		
		String sentence = "ilikesamsungmobile";
		WordBreak wb = new WordBreak(dictionary);
		List<String> words = wb.breakWord(sentence);
		
		Assert.assertEquals(4, words.size());
		
		int count = 0;
		for(String str : words) {
			if(str.equals("i like samsung mobile") ||
					str.equals("i like sam sung mobile")||
					str.equals("i like samsung mob ile") ||
					str.equals("i like sam sung mob ile")) {
				count++;
			}
		}
		Assert.assertEquals(4, count);
		
		dictionary.removeCustomized();
	}
	
	/**
	 * �����û��ֵ�Ͼ䣨���ڶ����������е�ȫ�����ʣ�
	 */
	@Test
	public void test10() {
		Dictionary dictionary = new Dictionary();
		dictionary.addCustomized("i,like,sam,sung,samsung,mobile,mon,go,mob,ile");
		
		dictionary.addCustomized("su,ng");
		
		String sentence = "ilikesamsungmobile";
		WordBreak wb = new WordBreak(dictionary);
		List<String> words = wb.breakWord(sentence);
		
		Assert.assertEquals(6, words.size());
		
		int count = 0;
		for(String str : words) {
			if(str.equals("i like sam sung mobile") ||
					str.equals("i like sam su ng mobile")||
					str.equals("i like sam sung mob ile") ||
					str.equals("i like sam su ng mob ile") ||
					str.equals("i like samsung mob ile") ||
					str.equals("i like samsung mobile")) {
				count++;
			}
		}
		Assert.assertEquals(6, count);
		
		dictionary.removeCustomized();
	}
}
