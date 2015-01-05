package com.xinlukou.mmengine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.xinlukou.mmcommon.Helper;

@SuppressWarnings("unchecked")
public class EngineDeparture {
    public List<RouteTraverseArray> allTraverse;
	public RBTree arvTraverse;
	public FixRBTree backTrackTree;
    public List<RouteBase> routeBaseList;
	public FixRBTree agctTree;
	public RBTree agctMap;
    public List<RouteAGCT> routeAGCTAll;
    public List<RouteAdjust> routeBaseDataList;
    public List<UIRoute> uiRouteList;
	public String debugMsg;
	
	////init
	private void initTraverse() {
		arvTraverse = new RBTree(6);
		int linkCount = DM.linkList.size();
		allTraverse = new ArrayList<RouteTraverseArray>(linkCount);
		for(int i = 0; i < linkCount; i++) {
			allTraverse.add(new RouteTraverseArray());
		}
	}
	
	private void initBackTrack() {
		backTrackTree = new FixRBTree(7, Define.MaxRouteCount);
		routeBaseList = new ArrayList<RouteBase>(Define.MaxRouteCount);
	}
	
	private void initAGCT() {
		agctTree = new FixRBTree(9, Define.MaxRouteCount);
		agctMap = new RBTree(16);
	}
	
	private boolean isLoopRouteBase(RouteBase routebase) {
		boolean result = false;
		List<Integer> passNodeList = new ArrayList<Integer>();
		Link firstLink = DM.getLink(routebase.firstLinkID);
		passNodeList.add(firstLink.fromStationID);
		passNodeList.add(firstLink.toStationID);
		for(int bindID : routebase.transferIDList) {
			Transfer trans = DM.getTransferFrom(bindID);
			Link link = DM.getLink(trans.toLinkID);
			if(!passNodeList.contains(link.toStationID)) {
				passNodeList.add(link.toStationID);
			} else {
				result = true;
				break;
			}
		}
		return result;
	}

	private void initAdjust() {
		routeBaseDataList = new ArrayList<RouteAdjust>();
		List<RouteAGCT> routeAGCTList = (List<RouteAGCT>)agctTree.getInOrderList();
		routeBaseList.clear();
		for(RouteAGCT routeAGCT : routeAGCTList) {
			if(!isLoopRouteBase(routeAGCT.routeBase)) {
				routeBaseList.add(routeAGCT.routeBase);
			}
		}
	}
	
	private void initFormat() {
		uiRouteList = new ArrayList<UIRoute>(5);
	}

	////output
	private Link getLink(TransferLink transLink) {
		if(transLink == null || transLink.linkID == -1) return null;
		else return DM.getLink(transLink.linkID);
	}
	
	private SrcUNO getSrcUNO(String uno) {
		SrcUNO result = null;
		String tempUNO = uno.toUpperCase();
		for(SrcUNO obj : DM.unoList) {
			if(obj.uno.equals(tempUNO)) {
				result = obj;
				break;
			}
		}
		return result;
	}

	private void outputRouteData(RouteData routeData, int num) {
		System.out.println(String.format("-----------------%d----------------", num));
		System.out.println(String.format("DepDateTime:%s", Util.convertDateToYYYYMMDDHHMM(routeData.depDateTime)));
		System.out.println(String.format("ArvDateTime:%s", Util.convertDateToYYYYMMDDHHMM(routeData.arvDateTime)));
		System.out.println(String.format("Time:%d", (routeData.driveTime + routeData.transTime)));
		System.out.println(String.format("Trans:%d", routeData.transCount));
		System.out.println(String.format("Distance:%d", routeData.distance));
		System.out.println("---------");
	}

	private void outputTraverse() {
		List<RouteTraverseInfo> routeDetailList = (List<RouteTraverseInfo>)arvTraverse.getInOrderList();
		for(int i = 0; i < routeDetailList.size(); i++) {
			outputRouteData(routeDetailList.get(i).routeTraverse.routeData, i + 1);
		}
	}

	private void outputBackTrack() {
//	NSMutableArray *routeDetailList = [backTrackTree getInOrderList];
//	for (int i = 0; i < routeDetailList.count; i++) {
//		MERouteTraverseDetail *detail = [routeDetailList objectAtIndex:i];
//		MERouteData *routeData = detail.routeTraverse.routeData;
//		NSMutableArray *transLinkList = detail.transLinkList;
//		[self outputRouteData:routeData num:i + 1];
//		for (int j = 0; j < transLinkList.count; j++) {
//			METransferLink *tempObj = [transLinkList objectAtIndex:j];
//			MELink *link = [self getLink:tempObj];
//			MELine *line = [MEDataManage getLineByID:[MEDataManage getWayByID:link.wayID].lineID];
//			NSLog(@"%@(%@--%@)", [self getSrcUNO:line.uno].english, [self getSrcUNO:[MEDataManage getStationByID:link.fromStationID].uno].english, [self getSrcUNO:[MEDataManage getStationByID:link.toStationID].uno].english);
//		}
//	}
	}

	private void outputAGCT() {
//	NSMutableArray *routeAGCTList = [agctTree getInOrderList];
//	for (int i = 0; i < routeAGCTList.count; i++) {
//		MERouteAGCT *routeAGCT = [routeAGCTList objectAtIndex:i];
//		MERouteData *routeData = routeAGCT.routePatternData.routeData;
//		[self outputRouteData:routeData num:i + 1];
//		MELink *firstLink = [MEDataManage getLinkByID:routeAGCT.routeBase.firstLinkID];
//		MEWay *tempWay = [MEDataManage getWayByID:firstLink.wayID];
//		MELine *firstLine = [MEDataManage getLineByID:tempWay.lineID];
//		NSLog(@"%@(%@--%@)", [self getSrcUNO:firstLine.uno].english, [self getSrcUNO:[MEDataManage getStationByID:firstLink.fromStationID].uno].english, [self getSrcUNO:[MEDataManage getStationByID:firstLink.toStationID].uno].english);
//		for (int j = 0; j < routeAGCT.routeBase.transferIDList.count; j++) {
//			MELink *link = [MEDataManage getLinkByID:[[MEDataManage getTransferFromByID:[routeAGCT.routeBase getTransferIDAt:j]] toLinkID]];
//			MELine *line = [MEDataManage getLineByID:[MEDataManage getWayByID:link.wayID].lineID];
//			NSLog(@"%@(%@--%@)", [self getSrcUNO:line.uno].english, [self getSrcUNO:[MEDataManage getStationByID:link.fromStationID].uno].english, [self getSrcUNO:[MEDataManage getStationByID:link.toStationID].uno].english);
//		}
//	}
	}

	private void outputAdjust() {
//	for(int i = 0; i < routeBaseDataList.count; i++) {
//		MERouteAdjust *tempObj = [routeBaseDataList objectAtIndex:i];
//		MERouteBase *routeBase = tempObj.routeBase;
//		MERouteData *routeData = tempObj.routeData;
//		[self outputRouteData:routeData num:i + 1];
//		MELink *firstLink = [MEDataManage getLinkByID:routeBase.firstLinkID];
//		MEWay *tempWay = [MEDataManage getWayByID:firstLink.wayID];
//		MELine *firstLine = [MEDataManage getLineByID:tempWay.lineID];
//		NSLog(@"%@(%@--%@)", [self getSrcUNO:firstLine.uno].english, [self getSrcUNO:[MEDataManage getStationByID:firstLink.fromStationID].uno].english, [self getSrcUNO:[MEDataManage getStationByID:firstLink.toStationID].uno].english);
//		for (int j = 0; j < routeBase.transferIDList.count; j++) {
//			MELink *link = [MEDataManage getLinkByID:[[MEDataManage getTransferFromByID:[routeBase getTransferIDAt:j]] toLinkID]];
//			MELine *line = [MEDataManage getLineByID:[MEDataManage getWayByID:link.wayID].lineID];
//			NSLog(@"%@(%@--%@)", [self getSrcUNO:line.uno].english, [self getSrcUNO:[MEDataManage getStationByID:link.fromStationID].uno].english, [self getSrcUNO:[MEDataManage getStationByID:link.toStationID].uno].english);
//		}
//	}
	}

	private void outputFormat() {
		for(UIRoute route : uiRouteList) {
			System.out.println(String.format("------------------%s------------------", route.index));
			System.out.println(String.format("DepDateTime					  : %s", route.depDT));
			System.out.println(String.format("ArvDateTime					  : %s", route.arvDT));
			System.out.println(String.format("TotalTime(DriveTime + OtherTime) : %s(%s+%s)", route.total, route.drive, route.other));
			System.out.println(String.format("TransCount					   : %s", route.count));
			System.out.println(String.format("Distance						 : %s", route.distance));
			System.out.println(String.format("Fare							 : %s", route.fare));
			for(UIDetail detail : route.detail) {
				String wayName = getSrcUNO(detail.way).english;
				if(!Helper.isEmpty(wayName)) wayName = String.format("(%s)", wayName);
				System.out.println(String.format("%s%s : %s--%s", getSrcUNO(detail.line).english, wayName, getSrcUNO(detail.depSta).english, getSrcUNO(detail.arvSta).english));
				System.out.println(String.format("( %s--%s )%sStations,%sRMB,%sTransType", detail.depDT, detail.arvDT, detail.count, detail.fare, detail.type));

			}
		}
	}
	
	////Traverse
	private Transfer getPreTrans(TransferLink transLink) {
		if(transLink == null || transLink.preTransferID == -1) return null;
		else return DM.getTransferFrom(transLink.preTransferID);
	}
	
	private int getPreTransLinkID(TransferLink transLink) {
		if(transLink == null || transLink.preTransferID == -1) return -1;
		else return DM.getTransferFrom(transLink.preTransferID).fromLinkID;
	}
	
	private RouteTraverseArray getAllTraverseAt(int index) {
		return allTraverse.get(index);
	}

	private RouteTraverseInfo getPreRouteInfo(RouteTraverseInfo routeInfo) {
		int preLinkID = getPreTransLinkID(routeInfo.routeTraverse.transLink);
		int preIndex = routeInfo.theIndex;
		return getAllTraverseAt(preLinkID).getAt(preIndex);
	}
	
	private void runTraverse() {
		boolean endFlag = false;
		RBTree bestTraverse = new RBTree(6);
		RouteTraverse curRoute = null;
		RouteTraverse preRoute = null;
		int curRouteIndex = -1;
		int preRouteIndex = -1;
		RouteTraverseInfo minObj;
		List<TransferLink> transLinkList = DM.getDepTransferLinks(Condition.depID);
		if(transLinkList.size() == 0) return;
		while(true) {
			for(TransferLink transLink : transLinkList) {
				//NSLog(@"While : TransLink = %d - %d", transLink.preTransferID, transLink.linkID);
				if(preRoute == null) curRoute = new RouteTraverse();
				else curRoute = new RouteTraverse(preRoute);
				if(!curRoute.append(transLink)) continue;
				RouteTraverseArray tempRTArray = getAllTraverseAt(transLink.linkID);
				if (tempRTArray.topIndex == -1) {
					//NSLog(@"While : TopIndex = -1 , Add Best");
					curRouteIndex = tempRTArray.add(curRoute, preRouteIndex);
					bestTraverse.insertUnique(new RouteTraverseInfo(curRoute, curRouteIndex));
					if(Condition.arvID == getLink(transLink).toStationID) {
						//NSLog(@"While : TopIndex = -1 , Add Arv");
						arvTraverse.insertUnique(new RouteTraverseInfo(curRoute, curRouteIndex));
					}
				} else {
					if(Compare.compareRouteTraverse(curRoute, tempRTArray.getTop().routeTraverse) == -1) {
						//NSLog(@"While : Compare == -1 , Remove Add Best");
						bestTraverse.remove(tempRTArray.getTop());
						curRouteIndex = tempRTArray.add(curRoute, preRouteIndex);
						bestTraverse.insertUnique(new RouteTraverseInfo(curRoute, curRouteIndex));
					} else {
						List<Integer> passNodeIDList = new ArrayList<Integer>();
						boolean loopFlag = false;
						int loopNodeID = getLink(transLink).toStationID;
						RouteTraverseInfo tempPreRouteInfo = new RouteTraverseInfo(curRoute, preRouteIndex);
						while(true) {
							int tempNodeID = getLink(tempPreRouteInfo.routeTraverse.transLink).fromStationID;
							if(passNodeIDList.contains(tempNodeID)) {
								loopFlag = true;
								break;
							}
							passNodeIDList.add(tempNodeID);
							if(loopNodeID == getLink(tempPreRouteInfo.routeTraverse.transLink).fromStationID) {
								loopFlag = true;
								break;
							}
							if(tempPreRouteInfo.theIndex == -1) break;
							tempPreRouteInfo = getPreRouteInfo(tempPreRouteInfo);
						}
						//NSLog(@"While : Compare <> -1 , CheckLoop = %@", (loopFlag ? @"YES" : @"NO"));
						if(!loopFlag) {
							curRouteIndex =tempRTArray.add(curRoute, preRouteIndex);
							if(Condition.arvID == getLink(transLink).toStationID) {
								//NSLog(@"While : Compare <> -1 , Add Arv");
								arvTraverse.insertUnique(new RouteTraverseInfo(curRoute, curRouteIndex));
							}
						}
					}
				}
			}
			do {
				if(bestTraverse.size == 0) {
					//NSLog(@"Do While : Break 0");
					endFlag = true;
					break;
				}
				minObj = (RouteTraverseInfo)bestTraverse.removeMin();
				preRoute = minObj.routeTraverse;
				preRouteIndex = minObj.theIndex;
				while(Condition.arvID == getLink(preRoute.transLink).toStationID) {
					//NSLog(@"Do While : Add Arv");
					arvTraverse.insertUnique(new RouteTraverseInfo(preRoute, preRouteIndex));
					if(bestTraverse.size == 0) {
						//NSLog(@"Do While : Break 0");
						endFlag = true;
						break;
					}
					minObj = (RouteTraverseInfo)bestTraverse.removeMin();
					preRoute = minObj.routeTraverse;
					preRouteIndex = minObj.theIndex;
				}
				if(endFlag) break;
				transLinkList = DM.getConnectFromTransferLinks(preRoute.transLink.linkID);
				//NSLog(@"Do While : TransLinkCount = %i", transLinkList.count);
			} while(transLinkList.size() == 0);
			if (endFlag) break;
		}
	}

	////BackTrack
	private List<Integer> getRouteChangePosition(List<TransferLink> transLinkList) {
		List<Integer> result = new ArrayList<Integer>();
		Transfer trans = null;
		for(int i = 0; i < transLinkList.size(); i++) {
			TransferLink tempObj = transLinkList.get(i);
			trans = getPreTrans(tempObj);
			if(trans == null) continue;
			if(getAllTraverseAt(tempObj.linkID).routeTraverseInfoList.size() > 1) result.add(i);
		}
		return result;
	}
	
	private boolean judgeRoute(List<PairInt> posList, List<TransferLink> transLinkList, RBTree posTree) {
		boolean result = false;
		List<Integer> positionList = getRouteChangePosition(transLinkList);
		if(positionList.size() == 0) {
			PairInt tempObj;
			for(int i = 0; i < posList.size(); i++) {
				tempObj = posList.get(i);
				if(posTree.insertUnique(tempObj)) {
					getAllTraverseAt(tempObj.value1).disable(tempObj.value2);
					result = true;
				}
			}
		} else {
			PairInt pos;
			for(int i = 0; i < positionList.size(); i++) {
				pos = posList.get(positionList.get(i));
				if(posTree.insertUnique(pos)) {
					getAllTraverseAt(pos.value1).disable(pos.value2);
					result = true;
				}
				
			}
		}
		return result;
	}
	
	private RouteBase transferLinkList2RouteBase(List<TransferLink> transLinkList) {
		RouteBase result = new RouteBase();
		result.firstLinkID = transLinkList.get(0).linkID;
		for(TransferLink transLink : transLinkList) {
			if(transLink.preTransferID != -1) result.addTransferID(transLink.preTransferID);
		}
		return result;
	}
	
	private void runBackTrack() {
		if(arvTraverse.size == 0) return;
		while(arvTraverse.size > 0) {
			RouteTraverseInfo tempArvRouteInfo = (RouteTraverseInfo)arvTraverse.removeMin();
			RBTree posTree = new RBTree(2);
			boolean flag = true;
			while(true) {
				RouteTraverse tempPreRoute = tempArvRouteInfo.routeTraverse;
				RouteTraverse tempRoute = new RouteTraverse(tempPreRoute);
				RBTree nodeTree = new RBTree(1);
				List<PairInt> posList = new ArrayList<PairInt>();
				int tempIndex = tempArvRouteInfo.theIndex;
				int tempLinkID = tempPreRoute.transLink.linkID;
				posList.add(new PairInt(tempLinkID, tempIndex));
				nodeTree.insertUnique(getLink(tempPreRoute.transLink).toStationID);
				nodeTree.insertUnique(getLink(tempPreRoute.transLink).fromStationID);
				while(getLink(tempPreRoute.transLink).fromStationID != Condition.depID) {
					tempIndex = getAllTraverseAt(tempLinkID).getAt(tempIndex).theIndex;
					tempLinkID = getPreTransLinkID(tempPreRoute.transLink);
					if(!getAllTraverseAt(tempLinkID).isEnable(tempIndex)) {
						int tempTop = getAllTraverseAt(tempLinkID).topIndex;
						if(tempTop != -1) {
							tempRoute.routeData.minus(getAllTraverseAt(tempLinkID).getAt(tempIndex).routeTraverse.routeData);
							tempIndex = tempTop;
							tempRoute.routeData.plus(getAllTraverseAt(tempLinkID).getAt(tempIndex).routeTraverse.routeData);
						}
					}
					posList.add(new PairInt(tempLinkID, tempIndex));
					tempPreRoute = getAllTraverseAt(tempLinkID).getAt(tempIndex).routeTraverse;
					if(!nodeTree.insertUnique(getLink(tempPreRoute.transLink).fromStationID)) {
						int loopNode = getLink(tempPreRoute.transLink).fromStationID;
						for(int j = posList.size() - 1; j >= 0; j--) {
							PairInt tempPI = posList.get(j);
							getAllTraverseAt(tempPI.value1).disable(tempPI.value2);
							if(loopNode == getLink(getAllTraverseAt(tempPI.value1).getAt(tempPI.value2).routeTraverse.transLink).toStationID) break;
						}
					}
				}
				Collections.reverse(posList);
				List<TransferLink> transLinkList = new ArrayList<TransferLink>(posList.size());
				for(int j = 0; j < posList.size(); j++) {
					PairInt tempPosObj = posList.get(j);
					transLinkList.add(getAllTraverseAt(tempPosObj.value1).getAt(tempPosObj.value2).routeTraverse.transLink);
				}
				flag = flag && judgeRoute(posList, transLinkList, posTree);
				flag = flag && backTrackTree.insertUnique(new RouteTraverseDetail(tempRoute, transLinkList));
				if(!flag) break;
			}
			while(posTree.size > 0) {
				PairInt tempPos = (PairInt)posTree.removeMin();
				getAllTraverseAt(tempPos.value1).enable(tempPos.value2);
			}
		}
		List<RouteTraverseDetail> detailRouteList = (List<RouteTraverseDetail>)backTrackTree.getInOrderList();
		for(RouteTraverseDetail detailRoute : detailRouteList) {
			routeBaseList.add(transferLinkList2RouteBase(detailRoute.transLinkList));
		}
	}
	
	////AGCT
	private boolean maxToAGCT(RoutePart part) {
		boolean result = false;
		if(agctTree.count() == Define.MaxRouteCount) {
			RouteAGCT tempObj = (RouteAGCT)agctTree.getMax();
			if(Compare.compareRouteData(part.routePatternData.routeData, tempObj.routePatternData.routeData) >= 0) result = true;
		}
		return result;
	}
	
	private RouteAGCT routeBase2RouteAGCT(RouteBase routeBase) {
		RouteAGCT result = new RouteAGCT(routeBase);
		if(result.routePatternData.routePattern.size() == 0) {
			result = null;
		}
		return result;
	}
	
	private boolean insertRouteAGCT(RouteAGCT routeAGCT) {
		boolean result = false;
		RouteAGCT tempResult = (RouteAGCT)agctMap.update(routeAGCT, 15);
		if(tempResult == null) result = agctTree.insertUnique(routeAGCT);
		else if(tempResult != routeAGCT) {
			agctTree.remove(tempResult);
			result = agctTree.insertUnique(routeAGCT);
		}
		return result;
	}
	
	private boolean connectRoutePart(RoutePart rpFront, RoutePart rpBehind, RouteAGCT[] routeAGCT) {
		boolean result = false;
		routeAGCT[0] = null;
		RouteAGCT rgFront = routeAGCTAll.get(rpFront.routeID);
		RouteAGCT rgBehind = routeAGCTAll.get(rpBehind.routeID);
		int frontLastLinkID = DM.getTransferFrom(rgFront.routeBase.getTransferIDAt(rpFront.endPos)).fromLinkID;
		int behindFirstLinkID = DM.getTransferFrom(rgBehind.routeBase.getTransferIDAt(rpBehind.startPos)).toLinkID;
		Link frontLastLink = DM.getLink(frontLastLinkID);
		Link behindFirstLink = DM.getLink(behindFirstLinkID);
		if(frontLastLink.fromStationID == behindFirstLink.toStationID) return result;
		RouteBase routeBase = new RouteBase();
		routeBase.firstLinkID = rgFront.routeBase.firstLinkID;
		for(int i = rpFront.startPos; i < rpFront.endPos; i++) {
			routeBase.transferIDList.add(rgFront.routeBase.transferIDList.get(i));
		}
		Transfer nextTrans;
		if(frontLastLink.startTransferFromID != -1 && frontLastLink.endTransferFromID != -1) {
			for(int i = frontLastLink.startTransferFromID; i < frontLastLink.endTransferFromID; i ++) {
				nextTrans = DM.getTransferFrom(i);
				if(nextTrans.toLinkID == behindFirstLinkID) {
					routeBase.addTransferID(nextTrans.id);
					for(int j = rpBehind.startPos + 1; j < rpBehind.endPos; j++) {
						routeBase.transferIDList.add(rgBehind.routeBase.transferIDList.get(j));
					}
					routeAGCT[0] = routeBase2RouteAGCT(routeBase);
					if(routeAGCT[0] != null) result = true;
					break;
				}
			}
		}
		return result;
	}

	private int connectAndInsert(RoutePart rpFront, RoutePart rpBehind, RouteAGCT[] routeAGCT) {
		int result = 0;
		RouteData routeData = new RouteData(rpFront.routePatternData.routeData);
		routeData.plus(rpBehind.routePatternData.routeData);
		if(agctTree.count() == Define.MaxRouteCount) {
			RouteAGCT tempObj = (RouteAGCT)agctTree.getMax();
			if(Compare.compareRouteData(routeData, tempObj.routePatternData.routeData) >= 0) result = -1;
		}
		if(result == 0 && connectRoutePart(rpFront, rpBehind, routeAGCT)) {
			if(insertRouteAGCT(routeAGCT[0])) result = 1;
		}
		return result;
	}

	private RoutePartManage makeRoutePartManage(int routeID, List<RouteAGCT> routeAGCTList) {
		RoutePartManage result = new RoutePartManage();
		for(int i = 0; i < routeAGCTList.size(); i++, routeID++) {
			RouteAGCT tempObj = routeAGCTList.get(i);
			if(tempObj.routeBase.transferIDList.size() == 0) continue;
			RouteAGCT rAGCT = new RouteAGCT(tempObj);
			RouteData routeDataFront = new RouteData();
			RouteData routeDataBehind = new RouteData(rAGCT.routePatternData.routeData);
			List<PairInt> routePatternFront = new ArrayList<PairInt>();
			List<PairInt> routePatternBehind = new ArrayList<PairInt>();
			routePatternBehind.addAll(rAGCT.routePatternData.routePattern);
			routeDataFront.append(rAGCT.routeBase.firstLinkID);
			routeDataBehind.transTime -= routeDataFront.transTime;
			Link tempLink = DM.getLink(rAGCT.routeBase.firstLinkID);
			routePatternFront.add(new PairInt(tempLink.wayID, tempLink.fromStationID));
			int tempPos = 0;
			Station tempSta;
			for(int bindID : rAGCT.routeBase.transferIDList) {
				routeDataBehind.remove(tempLink.id, bindID);
				if(routePatternBehind.size() > 1) {
					routePatternBehind.remove(0);
				}
				tempSta = DM.getStation(tempLink.toStationID);
				if(tempSta.multiLine == 1 || tempSta.multiWay == 1) {
					RoutePart partFront = new RoutePart(routeID, 0, tempPos, tempLink.toStationID, routePatternFront, routeDataFront);
					RoutePart partBehind = new RoutePart(routeID, tempPos, rAGCT.routeBase.transferIDList.size(), tempLink.toStationID, routePatternBehind, routeDataBehind);
					result.insert(tempLink.toStationID, partFront, partBehind);
				}
				tempPos++;
				Transfer tempTrans = DM.getTransferFrom(bindID);
				tempLink = DM.getLink(tempTrans.toLinkID);
				routeDataFront.append(bindID, tempTrans.toLinkID);
				routePatternFront.add(new PairInt(tempLink.wayID, tempLink.fromStationID));
			}
		}
		return result;
	}
	
	private List<RouteAGCT> nextAGCT(RoutePartManage rpmBase, List<RouteAGCT> routeAGCTList) {
		List<RouteAGCT> result = new ArrayList<RouteAGCT>();
		RoutePartManage rpmAdd = makeRoutePartManage(routeAGCTAll.size(), routeAGCTList);
		routeAGCTAll.addAll(routeAGCTList);
		int connectNodeCount = rpmAdd.connectNodeList.size();
		List<RoutePart> rpFrontList;
		List<RoutePart> rpBehindList;
		RBTree rbFront = null;
		RBTree rbBehind = null;
		List<RoutePart> rpFrontBaseList;
		List<RoutePart> rpBehindBaseList;
		for(int i = 0; i < connectNodeCount; i++) {
			rpFrontList = (List<RoutePart>)rpmAdd.getFrontAt(i).getInOrderList();
			rpBehindList = (List<RoutePart>)rpmAdd.getBehindAt(i).getInOrderList();
			int connectNodeID = rpmAdd.getConnectNodeAt(i);
			int indexBase = rpmBase.connectNodeList.indexOf(connectNodeID);
			if(indexBase != -1) {
				rbFront = rpmBase.getFrontAt(indexBase);
				rbBehind = rpmBase.getBehindAt(indexBase);
			} else {
				rbFront = null;
				rbBehind = null;
			}
			for(RoutePart rpFront : rpFrontList) {
				if(maxToAGCT(rpFront)) break;
				if(!rpmBase.insertFront(connectNodeID, rpFront)) continue;
				if(rbBehind == null || rbBehind.size <= 0) continue;
				rpBehindBaseList = (List<RoutePart>)rbBehind.getInOrderList();
				for(RoutePart rpBehindBase : rpBehindBaseList) {
					if(maxToAGCT(rpBehindBase)) break;
					RouteAGCT[] routeAGCT = new RouteAGCT[1];
					int tempResult = connectAndInsert(rpFront, rpBehindBase, routeAGCT);
					if(tempResult == -1) break;
					if(tempResult == 1) result.add(routeAGCT[0]);
				}
			}
			for(RoutePart rpBehind : rpBehindList) {
				if(maxToAGCT(rpBehind)) break;
				if(!rpmBase.insertBehind(connectNodeID, rpBehind)) continue;
				if(rbFront == null || rbFront.size <= 0) continue;
				rpFrontBaseList = (List<RoutePart>)rbFront.getInOrderList();
				for(RoutePart rpFrontBase : rpFrontBaseList) {
					if(maxToAGCT(rpFrontBase)) break;
					RouteAGCT[] routeAGCT = new RouteAGCT[1];
					int tempResult = connectAndInsert(rpFrontBase, rpBehind, routeAGCT);
					if(tempResult == -1) break;
					if(tempResult == 1) result.add(routeAGCT[0]);
				}
			}
		}
		return result;
	}
	
	private void runAGCT() {
		for(RouteBase routebase : routeBaseList) {
			RouteAGCT routeAGCT = routeBase2RouteAGCT(routebase);
			if(routeAGCT != null) insertRouteAGCT(routeAGCT);
		}
		routeAGCTAll = (List<RouteAGCT>)agctTree.getInOrderList();
		if(agctTree.count() <= 0) return;
		RoutePartManage rpmBase = makeRoutePartManage(0, routeAGCTAll);
		List<RouteAGCT> routeAGCTList = new ArrayList<RouteAGCT>();
		for(int i = 0; i < rpmBase.connectNodeList.size(); i++) {
			RBTree rbFront = rpmBase.getFrontAt(i);
			RBTree rbBehind = rpmBase.getBehindAt(i);
			if(rbFront.size > 1 && rbBehind.size > 1) {
				List<RoutePart> rpFrontBaseList = (List<RoutePart>)(Object)rbFront.getInOrderList();
				List<RoutePart> rpBehindBaseList = (List<RoutePart>)(Object)rbBehind.getInOrderList();
				for(RoutePart rpFrontBase : rpFrontBaseList) {
					if(maxToAGCT(rpFrontBase)) break;
					for(RoutePart rpBehindBase : rpBehindBaseList) {
						if(maxToAGCT(rpBehindBase)) break;
						RouteAGCT[] routeAGCT = new RouteAGCT[1];
						int tempResult = connectAndInsert(rpFrontBase, rpBehindBase, routeAGCT);
						if(tempResult == -1) break;
						if(tempResult == 1) routeAGCTList.add(routeAGCT[0]);
					}
				}
			}
		}
		if(routeAGCTList.size() > 0) {
			List<RouteAGCT> tempList = routeAGCTList;
			for(int i = 0; i < Define.MaxAGCTCount; i++) {
				List<RouteAGCT> tempRouteAGCTList = nextAGCT(rpmBase, tempList);
				if(tempRouteAGCTList.size() == 0) break;
				tempList = tempRouteAGCTList;
			}
		}
	}
	
	////Adjust
	private RouteData getTopSearchRouteData(RouteBase routebase, Date searchDT) {
		Condition.commitSearchDT(searchDT);
		Condition.commitSearchType(Define.SearchType_Time);
		RouteData result = new RouteData();
		boolean flag = true;
		flag = result.append(routebase.firstLinkID);
		for(int transID : routebase.transferIDList) {
			int nextLinkID = DM.getTransferFrom(transID).toLinkID;
			flag = flag && result.append(transID, nextLinkID);
		}
		if(!flag) {
			result = null;
		}
		Condition.revertSearchDT();
		Condition.revertSearchType();
		return result;
	}
	
	private RouteData getTopRouteData(RouteBase routebase, Date searchDT) {
		RouteData result = getTopSearchRouteData(routebase, searchDT);
		if(result != null) {
			while(true) {
				Date tempSearchDT = new Date(result.depDateTime.getTime() + 1 * Define.UNIT_MIN);
				RouteData temp = getTopSearchRouteData(routebase, tempSearchDT);
				if(temp != null && temp.arvDateTime.equals(result.arvDateTime)) result = temp;
				else break;
			}
		}
		return result;
	}
	
	private List<RouteData> getTopRouteDataList(RouteBase routebase, int count) {
		List<RouteData> result = new ArrayList<RouteData>(count);
		Date searchDT = Condition.searchDT;
		while(count > 0) {
			RouteData temp = getTopRouteData(routebase, searchDT);
			if(temp == null) break;
			searchDT = new Date(temp.depDateTime.getTime() + 1 * Define.UNIT_MIN);
			result.add(temp);
			count--;
		}
		return result;
	}
	
	private boolean isSameLineLink(int linkID1, int linkID2) {
		if(linkID1 == linkID2) return true;
		Link link1 = DM.getLink(linkID1);
		Link link2 = DM.getLink(linkID2);
		Way way1 = DM.getWay(link1.wayID);
		Way way2 = DM.getWay(link2.wayID);
		return way1.lineID == way2.lineID;
	}

	private boolean isSameLineBind(int transferID1, int transferID2) {
		if(transferID1 == transferID2) return true;
		Transfer trans1 = DM.getTransferFrom(transferID1);
		Transfer trans2 = DM.getTransferFrom(transferID2);
		return isSameLineLink(trans1.toLinkID, trans2.toLinkID);
	}

	private boolean isSameLineRoute(RouteAdjust obj1, RouteAdjust obj2) {
		boolean result = true;
		RouteBase rb1 = obj1.routeBase;
		RouteBase rb2 = obj2.routeBase;
		if(!isSameLineLink(rb1.firstLinkID, rb2.firstLinkID)) result = false;
		else if(rb1.transferIDList.size() != rb2.transferIDList.size()) result = false;
		else {
			for(int i = 0; i < rb1.transferIDList.size(); i++) {
				int temp1 = rb1.getTransferIDAt(i);
				int temp2 = rb2.getTransferIDAt(i);
				if(!isSameLineBind(temp1, temp2)) {
					result = false;
					break;
				}
			}
		}
		return result;
	}

	private void sortRouteAdjust(List<RouteAdjust> list) {
		RouteAdjust obj1;
		RouteAdjust obj2;
		for(int i = 0; i < list.size(); i++) {
			obj1 = list.get(i);
			for(int j = i + 1; j < list.size(); j ++) {
				obj2 = list.get(j);
				if(Compare.compareRouteData(obj1.routeData, obj2.routeData) == 1) {
					list.set(i, obj2);
					list.set(j, obj1);
					obj1 = obj2;
				}
			}
		}
	}

	private void runAdjust() {
		if(routeBaseList.size() <= 0) return;
		int count = Condition.resultRouteCount;
		if(routeBaseList.size() == 1) {
			List<RouteData> routedataList = getTopRouteDataList(routeBaseList.get(0), count);
			for(RouteData routedata : routedataList) {
				RouteAdjust temp = new RouteAdjust(routedata, routeBaseList.get(0));
				routeBaseDataList.add(temp);
			}
		} else if(1 < routeBaseList.size() && routeBaseList.size() < count) {
			List<RouteAdjust> tempResult = new ArrayList<RouteAdjust>();
			for(RouteBase routebase : routeBaseList) {
				List<RouteData> routedataList = getTopRouteDataList(routebase, count - routeBaseList.size() + 1);
				for(int i = 0; i < routedataList.size(); i++) {
					RouteAdjust temp = new RouteAdjust(routedataList.get(i), routebase);
					if(i == 0) routeBaseDataList.add(temp);
					else tempResult.add(temp);
				}
			}
			sortRouteAdjust(tempResult);
			while(routeBaseDataList.size() < count && tempResult.size() > 0) {
				routeBaseDataList.add(tempResult.get(0));
				tempResult.remove(0);
			}
		} else if(routeBaseList.size() >= count) {
			for(RouteBase routebase : routeBaseList) {
				List<RouteData> routedataList = getTopRouteDataList(routebase, 1);
				if(routedataList.size() > 0) {
					RouteAdjust temp = new RouteAdjust(routedataList.get(0), routebase);
					routeBaseDataList.add(temp);
				}
			}
		}
		sortRouteAdjust(routeBaseDataList);
		if(routeBaseDataList.size() > count) {
			List<RouteAdjust> tempInList = new ArrayList<RouteAdjust>();
			List<RouteAdjust> tempOutList = new ArrayList<RouteAdjust>();
			for(RouteAdjust temp : routeBaseDataList) {
				if(tempInList.size() < count) {
					boolean addFlag = true;
					for(RouteAdjust tempIn : tempInList) {
						if(isSameLineRoute(tempIn, temp)) {
							addFlag = false;
							break;
						}
					}
					if(addFlag) tempInList.add(temp);
					else tempOutList.add(temp);
				} else {
					tempOutList.add(temp);
				}
			}
			routeBaseDataList.clear();
			routeBaseDataList.addAll(tempInList);
			routeBaseDataList.addAll(tempOutList);
			while(routeBaseDataList.size() > count) {
				routeBaseDataList.remove(routeBaseDataList.size() - 1);
			}
			sortRouteAdjust(routeBaseDataList);
		}
		for(RouteAdjust routebaseData : routeBaseDataList) {
			if(routebaseData.routeData != null) {
				routebaseData.routeData.transTime = (int)(routebaseData.routeData.arvDateTime.getTime() - Condition.searchDT.getTime()) / Define.UNIT_MIN - routebaseData.routeData.driveTime;
			}
		}
	}

	////Format
	private String getFareStr(float fare) {
		if(fare == 0) return "0";
		int tempFare = (int)(fare * 100);
		int tempFare1 = tempFare / 100;
		int tempFare2 = tempFare % 100;
		int tempFare3 = tempFare % 10;
		if(tempFare3 == 0) {
			if(tempFare2 == 0) return String.format("%d", tempFare1);
			else return String.format("%d.%d", tempFare1, tempFare2);
		} else {
			return String.format("%d.%d%d", tempFare1, tempFare2, tempFare3);
		}
	}
	
	private float calcFare(String wayUNO, String depUNO, String arrUNO) {
		float result = 0;
		for(SrcFare srcFare : DM.fareList) {
			if(srcFare.wayList.contains(wayUNO)) {
				if(Helper.isEmpty(srcFare.commonFare)) {
					int rowIndex = srcFare.unoList.indexOf(depUNO);
					int colIndex = srcFare.unoList.indexOf(arrUNO);
					result = srcFare.getFare(rowIndex, colIndex);
				} else {
					result = Float.parseFloat(srcFare.commonFare);
				}
			}
		}
		return result;
	}
	
	private void modifyUIRoute(UIRoute route, RouteData routedata) {
		// Modify Time
		route.total = String.format("%d", (routedata.arvDateTime.getTime() - routedata.depDateTime.getTime()) / Define.UNIT_MIN);
		route.other = String.format("%d", Integer.parseInt(route.total) - Integer.parseInt(route.drive));
		// Modify TransferCount
		for(int i = 0; i < route.detail.size(); i++) {
			UIDetail tempDetail = route.detail.get(i);
			for(SrcUNO uno : DM.unoList) {
				if(uno.uno.equals(tempDetail.way)) {
					if(uno.type.equals(Define.UNOType_WW)) {
						int transferCount = Integer.parseInt(route.count);
						if(transferCount > 0) {
							route.count = String.format("%d", (transferCount - 1));
						}
					}
				}
			}
		}
		// Modify Fare
		String depStaUNO = "";
		String arrStaUNO = "";
		int depDetailIndex = -1;
		for(int i = 0; i < route.detail.size(); i++) {
			UIDetail curDetail = route.detail.get(i);
			if(i == 0) {
				depStaUNO = curDetail.depSta;
				arrStaUNO = curDetail.arvSta;
				depDetailIndex = i;
			} else {
				UIDetail depDetail = route.detail.get(depDetailIndex);
				boolean sameFareTypeFlag = false;
				for(SrcFare srcFare : DM.fareList) {
					if(srcFare.wayList.contains(depDetail.way) && srcFare.wayList.contains(curDetail.way)) {
						sameFareTypeFlag = true;
						break;
					}
				}
				if(sameFareTypeFlag) {
					arrStaUNO = curDetail.arvSta;
				} else {
					depDetail.fare = getFareStr(calcFare(depDetail.way, depStaUNO, arrStaUNO));
					depStaUNO = curDetail.depSta;
					arrStaUNO = curDetail.arvSta;
					depDetailIndex = i;
				}
			}
			UIDetail tempDetail = route.detail.get(depDetailIndex);
			tempDetail.fare = getFareStr(calcFare(tempDetail.way, depStaUNO, arrStaUNO));
		}
		float fareCount = 0;
		for(int i = 0; i < route.detail.size(); i++) {
			UIDetail curDetail = route.detail.get(i);
			if(Helper.isEmpty(curDetail.fare)) {
				curDetail.fare = "0";
			} else {
				fareCount += Float.parseFloat(curDetail.fare);
			}
		}
		route.fare = getFareStr(fareCount);
	}
	
	private UIRoute getUIRoute(RouteBase routebase, RouteData routedata) {
		UIRoute route = new UIRoute();
		route.depDT = Util.convertDateToYYYYMMDDHHMM(routedata.depDateTime);
		route.arvDT = Util.convertDateToYYYYMMDDHHMM(routedata.arvDateTime);
		route.total = String.format("%d", (routedata.driveTime + routedata.transTime));
		route.drive = String.format("%d", routedata.driveTime);
		route.other = String.format("%d", routedata.transTime);
		route.distance = String.format("%d", routedata.distance);
		route.fare = "";
		route.count = String.format("%d", routedata.transCount);
		Condition.commitSearchDT(routedata.depDateTime);
		Condition.commitSearchType(Define.SearchType_Time);
		RouteData tempRouteData = new RouteData();
		tempRouteData.append(routebase.firstLinkID);
		Link curLink = DM.getLink(routebase.firstLinkID);
		Way curWay = DM.getWay(curLink.wayID);
		Line curLine = DM.getLine(curWay.lineID);
		Transfer trans = null;
		Link nextLink = null;
		Way nextWay = null;
		Line nextLine = null;
		UIDetail detail = new UIDetail();
		detail.depSta = DM.getStation(Condition.depID).uno;
		detail.depDT = route.depDT;
		int tempStaCount = 1;
		int tempDistance = curLink.distance;
		for(int i = 0; i < routebase.transferIDList.size(); i++) {
			trans = DM.getTransferFrom(routebase.getTransferIDAt(i));
			nextLink = DM.getLink(trans.toLinkID);
			nextWay = DM.getWay(nextLink.wayID);
			nextLine = DM.getLine(nextWay.lineID);
			if(trans.transType == Define.TransType_None) {
				tempStaCount++;
				tempDistance = tempDistance + nextLink.distance;
				detail.staList.add(DM.getStation(curLink.toStationID).uno);
				detail.timeList.add(Util.convertDateToYYYYMMDDHHMM(tempRouteData.arvDateTime));
				tempRouteData.append(trans.id, nextLink.id);
			} else {
				detail.arvSta = DM.getStation(curLink.toStationID).uno;
				detail.arvDT = Util.convertDateToYYYYMMDD(tempRouteData.arvDateTime);
				detail.count = String.format("%d", tempStaCount);
				detail.line = curLine.uno;
				detail.way = curWay.uno;
				detail.fare = "";
				detail.type = String.format("%d", trans.transType);
				detail.distance = String.format("%d", tempDistance);
				route.detail.add(detail);
				int preDrive = tempRouteData.driveTime;
				tempRouteData.append(trans.id, nextLink.id);
				int nowDrive = tempRouteData.driveTime;
				tempStaCount = 1;
				tempDistance = nextLink.distance;
				detail = new UIDetail();
				detail.depSta = DM.getStation(nextLink.fromStationID).uno;
				detail.depDT = Util.convertDateToYYYYMMDD(new Date(tempRouteData.arvDateTime.getTime() + (preDrive - nowDrive) * Define.UNIT_MIN));
			}
			curLink = nextLink;
			curWay = nextWay;
			curLine = nextLine;
		}
		detail.arvSta = DM.getStation(Condition.arvID).uno;
		detail.arvDT = route.arvDT;
		detail.count = String.format("%d", tempStaCount);
		detail.distance = String.format("%d", tempDistance);
		detail.line = curLine.uno;
		detail.way = curWay.uno;
		detail.fare = "";
		detail.type = String.format("%d", Define.TransType_None);
		detail.distance = String.format("%d", tempDistance);
		route.detail.add(detail);
		modifyUIRoute(route, routedata);
		Condition.revertSearchDT();
		Condition.revertSearchType();
		return route;
	}

	private void sortUIRouteList() {
		UIRoute obj1;
		UIRoute obj2;
		if(Condition.searchType == Define.SearchType_Time) {
			for(int i = 0; i < uiRouteList.size(); i++) {
				obj1 = uiRouteList.get(i);
				for(int j = i + 1; j < uiRouteList.size(); j++) {
					obj2 = uiRouteList.get(j);
					if(Compare.compareUIRouteForTime(obj1, obj2) == 1) {
						uiRouteList.set(i, obj2);
						uiRouteList.set(j, obj1);
						obj1 = obj2;
					}
				}
			}
		} else if(Condition.searchType == Define.SearchType_Transfer) {
			for(int i = 0; i < uiRouteList.size(); i++) {
				obj1 = uiRouteList.get(i);
				for(int j = i + 1; j < uiRouteList.size(); j++) {
					obj2 = uiRouteList.get(j);
					if(Compare.compareUIRouteForTransfer(obj1, obj2) == 1) {
						uiRouteList.set(i, obj2);
						uiRouteList.set(j, obj1);
						obj1 = obj2;
					}
				}
			}
		}
	}

	private void runFormat() {
		if(routeBaseDataList.size() == 0) return;
		sortRouteAdjust(routeBaseDataList);
		for(RouteAdjust routebaseData : routeBaseDataList) {
			uiRouteList.add(getUIRoute(routebaseData.routeBase, routebaseData.routeData));
		}
		// ReSort Route For HongKong Walk Line
		if(Condition.city.equals(Define.UNORange_HK) || Condition.city.equals(Define.UNORange_TB)) {
			sortUIRouteList();
		}
		for(int i = 0; i < uiRouteList.size(); i++) {
			uiRouteList.get(i).index = String.format("%d", i + 1);
		}
	}

	public List<UIRoute> run() {
		initTraverse();
		runTraverse();
		outputTraverse();
		initBackTrack();
		runBackTrack();
		outputBackTrack();
		initAGCT();
		runAGCT();
		outputAGCT();
		initAdjust();
		runAdjust();
		outputAdjust();
		initFormat();
		runFormat();
		outputFormat();
		return uiRouteList;
	}
}

