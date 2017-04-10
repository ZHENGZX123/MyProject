package cn.kwim.mqttcilent.common.views.sort;

import java.util.Comparator;

import cn.kwim.mqttcilent.common.cache.javabean.GroupListMember;

/**
 * 
 * @author hmg
 *
 */
public class PinyinComparator implements Comparator<GroupListMember> {

	public int compare(GroupListMember o1, GroupListMember o2) {
		if (o1.getSortLetters().equals("@")
				|| o2.getSortLetters().equals("#")) {
			return -1;
		} else if (o1.getSortLetters().equals("#")
				|| o2.getSortLetters().equals("@")) {
			return 1;
		} else {
			return o1.getSortLetters().compareTo(o2.getSortLetters());
		}
	}

}
