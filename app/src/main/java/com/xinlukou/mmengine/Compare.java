package com.xinlukou.mmengine;

import java.util.Date;
import java.util.List;

public class Compare {
	public static int compareString(String obj1, String obj2) {
		return obj1.compareTo(obj2);
	}

	public static int compareInt(int obj1, int obj2) {
		if (obj1 < obj2) return -1;
		else if (obj1 > obj2) return 1;
		else return 0;
	}

	public static int comparePairInt(PairInt obj1, PairInt obj2) {
		int result = 0;
		if(result == 0) result = compareInt(obj1.value1, obj2.value1);
		if(result == 0) result = compareInt(obj1.value2, obj2.value2);
		return result;
	}

	public static int compareDateTime(Date obj1, Date obj2) {
		return obj1.compareTo(obj2);
	}

	public static int compareRouteData(RouteData obj1, RouteData obj2) {
		int result = 0;
		if(Condition.flag) {
			if(Condition.searchType == Define.SearchType_Time) {
				if(result == 0) result = compareDateTime(obj1.arvDateTime, obj2.arvDateTime);
				if(result == 0) result = compareDateTime(obj1.depDateTime, obj2.depDateTime) * -1;
				if(result == 0) result = compareInt(obj1.transCount, obj2.transCount);
				if(result == 0) result = compareInt(obj1.distance, obj2.distance);
			} else if(Condition.searchType == Define.SearchType_Transfer) {
				int timeA = obj1.driveTime + obj1.transTime;
				int timeB = obj2.driveTime + obj2.transTime;
				if(result == 0) result = compareInt(obj1.transCount, obj2.transCount);
				if(result == 0) result = compareDateTime(obj1.arvDateTime, obj2.arvDateTime);
				if(result == 0) result = compareDateTime(obj1.depDateTime, obj2.depDateTime) * -1;
				if(result == 0) result = compareInt(timeA, timeB);
				if(result == 0) result = compareInt(obj1.distance, obj2.distance);
			} else if(Condition.searchType == Define.SearchType_Distance) {
				int timeA = obj1.driveTime + obj1.transTime;
				int timeB = obj2.driveTime + obj2.transTime;
				if(result == 0) result = compareInt(obj1.distance, obj2.distance);
				if(result == 0) result = compareDateTime(obj1.arvDateTime, obj2.arvDateTime);
				if(result == 0) result = compareDateTime(obj1.depDateTime, obj2.depDateTime) * -1;
				if(result == 0) result = compareInt(timeA, timeB);
				if(result == 0) result = compareInt(obj1.transCount, obj2.transCount);
			}
		} else {
			if(Condition.searchType == Define.SearchType_Time) {
				if(result == 0) result = compareDateTime(obj1.depDateTime, obj2.depDateTime) * -1;
				if(result == 0) result = compareDateTime(obj1.arvDateTime, obj2.arvDateTime);
				if(result == 0) result = compareInt(obj1.transCount, obj2.transCount);
				if(result == 0) result = compareInt(obj1.distance, obj2.distance);
			} else if(Condition.searchType == Define.SearchType_Transfer) {
				int timeA = obj1.driveTime + obj1.transTime;
				int timeB = obj2.driveTime + obj2.transTime;
				if(result == 0) result = compareInt(obj1.transCount, obj2.transCount);
				if(result == 0) result = compareDateTime(obj1.depDateTime, obj2.depDateTime) * -1;
				if(result == 0) result = compareDateTime(obj1.arvDateTime, obj2.arvDateTime);
				if(result == 0) result = compareInt(timeA, timeB);
				if(result == 0) result = compareInt(obj1.distance, obj2.distance);
			} else if(Condition.searchType == Define.SearchType_Distance) {
				int timeA = obj1.driveTime + obj1.transTime;
				int timeB = obj2.driveTime + obj2.transTime;
				if(result == 0) result = compareInt(obj1.distance, obj2.distance);
				if(result == 0) result = compareDateTime(obj1.depDateTime, obj2.depDateTime) * -1;
				if(result == 0) result = compareDateTime(obj1.arvDateTime, obj2.arvDateTime);
				if(result == 0) result = compareInt(timeA, timeB);
				if(result == 0) result = compareInt(obj1.transCount, obj2.transCount);
			}
		}
		return result;
	}

	public static int compareRouteTraverse(RouteTraverse obj1, RouteTraverse obj2) {
		int result = 0;
		if(obj1 != null && obj2 != null) {
			if(result == 0) result = compareRouteData(obj1.routeData, obj2.routeData);
			if(result == 0) result = compareInt(obj1.transLink.linkID, obj2.transLink.linkID);
			if(result == 0) result = compareInt(obj1.transLink.preTransferID, obj2.transLink.preTransferID);
		}
		return result;
	}

	public static int compareRouteTraverseInfo(RouteTraverseInfo obj1, RouteTraverseInfo obj2) {
		return compareRouteTraverse(obj1.routeTraverse, obj2.routeTraverse);
	}

	public static int compareRouteTraverseDetail(RouteTraverseDetail obj1, RouteTraverseDetail obj2) {
		int result = 0;
		if(result == 0) result = compareRouteTraverse(obj1.routeTraverse, obj2.routeTraverse);
		if(result == 0) result = compareInt(obj1.transLinkList.size(), obj2.transLinkList.size());
		if(result == 0) {
			int tempCount = obj1.transLinkList.size();
			TransferLink val1;
			TransferLink val2;
			for(int i = 0; i < tempCount; i++) {
				val1 = obj1.transLinkList.get(i);
				val2 = obj2.transLinkList.get(i);
				result = compareInt(val1.linkID, val2.linkID);
				if(result != 0) break;
			}
		}
		return result;
	}

	public static int compareRouteBase(RouteBase obj1, RouteBase obj2) {
		int result = 0;
		if(result == 0) result = compareInt(obj1.firstLinkID, obj2.firstLinkID);
		if(result == 0) result = compareInt(obj1.transferIDList.size(), obj2.transferIDList.size());
		if(result == 0) {
			int tempCount = obj1.transferIDList.size();
			int val1;
			int val2;
			for(int i = 0; i < tempCount; i++) {
				val1 = obj1.transferIDList.get(i);
				val2 = obj2.transferIDList.get(i);
				result = compareInt(val1, val2);
				if(result != 0) break;
			}
		}
		return result;
	}

	public static int compareRouteAGCT(RouteAGCT obj1, RouteAGCT obj2) {
		int result = 0;
		if(result == 0) result = compareRouteData(obj1.routePatternData.routeData, obj2.routePatternData.routeData);
		if(result == 0) result = compareRouteBase(obj1.routeBase, obj2.routeBase);
		return result;
	}

	public static int compareRouteAGCTData(RouteAGCT obj1, RouteAGCT obj2) {
		return compareRouteData(obj1.routePatternData.routeData, obj2.routePatternData.routeData);
	}

	public static int compareRouteAGCTPattern(RouteAGCT obj1, RouteAGCT obj2) {
		return compareRoutePattern(obj1.routePatternData.routePattern, obj2.routePatternData.routePattern);
	}

	public static int compareRoutePattern(List<PairInt> obj1, List<PairInt> obj2) {
		int result = 0;
		if(result == 0) result = compareInt(obj1.size(), obj2.size());
		if(result == 0) {
			int tempCount = obj1.size();
			PairInt val1;
			PairInt val2;
			for(int i = 0; i < tempCount; i++) {
				val1 = obj1.get(i);
				val2 = obj2.get(i);
				result = comparePairInt(val1, val2);
				if(result != 0) break;
			}
		}
		return result;
	}

	public static int compareRoutePatternData(RoutePatternData obj1, RoutePatternData obj2) {
		int result = 0;
		if(result == 0) result = compareRouteData(obj1.routeData, obj2.routeData);
		if(result == 0) result = compareRoutePattern(obj1.routePattern, obj2.routePattern);
		return result;
	}

	public static int compareRoutePart(RoutePart obj1, RoutePart obj2) {
		return compareRoutePatternData(obj1.routePatternData, obj2.routePatternData);
	}

	public static int compareRoutePatternDataValue(RoutePatternData obj1, RoutePatternData obj2) {
		return compareRouteData(obj1.routeData, obj2.routeData);
	}

	public static int compareRoutePatternDataKey(RoutePatternData obj1, RoutePatternData obj2) {
		return compareRoutePattern(obj1.routePattern, obj2.routePattern);
	}

	public static int compareFloat(float obj1, float obj2) {
		if (obj1 < obj2) return -1;
		else if (obj1 > obj2) return 1;
		else return 0;
	}

	public static int compareUIRouteForTime(UIRoute obj1, UIRoute obj2) {
		int result = 0;
		if(Condition.flag) {
			if(result == 0) result = compareString(obj1.arvDT, obj2.arvDT);
			if(result == 0) result = compareString(obj1.depDT, obj2.depDT) * -1;
			if(result == 0) result = compareInt(Integer.parseInt(obj1.count), Integer.parseInt(obj2.count));
		} else {
			if(result == 0) result = compareString(obj1.depDT, obj2.depDT) * -1;
			if(result == 0) result = compareString(obj1.arvDT, obj2.arvDT);
			if(result == 0) result = compareInt(Integer.parseInt(obj1.count), Integer.parseInt(obj2.count));
		}
		if(result == 0) {
			String[] list1 = obj1.fare.split("-");
			String[] list2 = obj2.fare.split("-");
			if(list1.length == 2 && list2.length == 2) {
				result = compareFloat(Float.parseFloat(list1[0]), Float.parseFloat(list2[0]));
			}
		}
		return result;
	}

	public static int compareUIRouteForTransfer(UIRoute obj1, UIRoute obj2) {
		int result = 0;
		if(Condition.flag) {
			if(result == 0) result = compareInt(Integer.parseInt(obj1.count), Integer.parseInt(obj2.count));
			if(result == 0) result = compareString(obj1.arvDT, obj2.arvDT);
			if(result == 0) result = compareString(obj1.depDT, obj2.depDT) * -1;
		} else {
			if(result == 0) result = compareInt(Integer.parseInt(obj1.count), Integer.parseInt(obj2.count));
			if(result == 0) result = compareString(obj1.depDT, obj2.depDT) * -1;
			if(result == 0) result = compareString(obj1.arvDT, obj2.arvDT);
		}
		if(result == 0) {
			String[] list1 = obj1.fare.split("-");
			String[] list2 = obj2.fare.split("-");
			if(list1.length == 2 && list2.length == 2) {
				result = compareFloat(Float.parseFloat(list1[0]), Float.parseFloat(list2[0]));
			}
		}
		return result;
	}
}
