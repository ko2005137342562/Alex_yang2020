package com.yang.wordbreak;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import lombok.Data;

/**
 * 
 * @author ╦увп
 *
 */
@Data
public class Word {

	private String content;;
	
	private Integer length;
	
	private Integer index;
	
	public boolean equals(Object obj) {
		if(this == obj){
			return true;
		}
		
		if(obj == null) {
			return false;
		}
		
		if(obj instanceof Word) {
			Word word = (Word)obj;
			if(StringUtils.equals(this.getContent(), word.getContent())) {
				return true;
			}
		}
		
		return false;
	}
	
	@Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (content == null ? 0 : content.hashCode());
        return result;
    }
}
