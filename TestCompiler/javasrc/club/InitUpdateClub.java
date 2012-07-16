package club;

import com.dexels.navajo.server.*;
import com.dexels.navajo.mapping.*;
import com.dexels.navajo.document.*;
import com.dexels.navajo.parser.*;
import java.util.ArrayList;
import java.util.HashMap;
import com.dexels.navajo.server.enterprise.tribe.TribeManagerFactory;
import com.dexels.navajo.mapping.compiler.meta.IncludeDependency;
import com.dexels.navajo.mapping.compiler.meta.ExpressionValueDependency;
import com.dexels.navajo.mapping.compiler.meta.SQLFieldDependency;
import com.dexels.navajo.mapping.compiler.meta.InheritDependency;
import com.dexels.navajo.mapping.compiler.meta.JavaDependency;
import com.dexels.navajo.mapping.compiler.meta.NavajoDependency;
import com.dexels.navajo.mapping.compiler.meta.Dependency;
import com.dexels.navajo.mapping.compiler.meta.AdapterFieldDependency;
import java.util.Stack;


/**
 * Generated Java code by TSL compiler.
 * $Id$
 *
 * Java version: Java HotSpot(TM) 64-Bit Server VM (1.6.0_33-b03-424-11M3720)
 * OS: Mac OS X 10.7.4
 *
 * WARNING NOTICE: DO NOT EDIT THIS FILE UNLESS YOU ARE COMPLETELY AWARE OF WHAT YOU ARE DOING
 *
 */

public final class InitUpdateClub extends CompiledScript {


private volatile static ArrayList<Dependency> dependentObjects = null;

  public InitUpdateClub() {
         if ( dependentObjects == null ) {
             dependentObjects = new ArrayList<Dependency>();
             setDependencies();
        }
  }

public final void setValidations() {
}

public final void finalBlock(Access access) throws Exception {
}
public final void execute(Access access) throws Exception { 

inDoc = access.getInDoc();
if (!kill) { execute_sub0(access); }
if (true) {
  com.dexels.navajo.document.Method m = NavajoFactory.getInstance().createMethod(access.getOutputDoc(), "club/ProcessQueryAnnualReports", "");
  m.setDescription("");
  m.addRequired("Club", "");
  access.getOutputDoc().addMethod(m);
}
if (true) {
  com.dexels.navajo.document.Method m = NavajoFactory.getInstance().createMethod(access.getOutputDoc(), "match/ProcessGetOpenReceiptMatches", "");
  m.setDescription("");
  m.addRequired("Club", "");
  access.getOutputDoc().addMethod(m);
}
if (true) {
  com.dexels.navajo.document.Method m = NavajoFactory.getInstance().createMethod(access.getOutputDoc(), "competition/ProcessCheckClubContradictions", "");
  m.setDescription("");
  m.addRequired("Club", "");
  access.getOutputDoc().addMethod(m);
}
if (true) {
  com.dexels.navajo.document.Method m = NavajoFactory.getInstance().createMethod(access.getOutputDoc(), "club/ProcessGetClubAccommodations", "");
  m.setDescription("");
  m.addRequired("Club", "");
  access.getOutputDoc().addMethod(m);
}
if (true) {
  com.dexels.navajo.document.Method m = NavajoFactory.getInstance().createMethod(access.getOutputDoc(), "club/ProcessQueryClubHomeAwayPreferences", "");
  m.setDescription("");
  m.addRequired("Club", "");
  access.getOutputDoc().addMethod(m);
}
if (true) {
  com.dexels.navajo.document.Method m = NavajoFactory.getInstance().createMethod(access.getOutputDoc(), "club/ProcessQueryDigitalCommunication", "");
  m.setDescription("");
  m.addRequired("Club", "");
  access.getOutputDoc().addMethod(m);
}
if (true) {
  com.dexels.navajo.document.Method m = NavajoFactory.getInstance().createMethod(access.getOutputDoc(), "club/ProcessQueryClub", "");
  m.setDescription("");
  m.addRequired("Club", "");
  access.getOutputDoc().addMethod(m);
}
if (true) {
  com.dexels.navajo.document.Method m = NavajoFactory.getInstance().createMethod(access.getOutputDoc(), "club/ProcessGetClubActivities", "");
  m.setDescription("");
  m.addRequired("Club", "");
  access.getOutputDoc().addMethod(m);
}
if (true) {
  com.dexels.navajo.document.Method m = NavajoFactory.getInstance().createMethod(access.getOutputDoc(), "club/ProcessQueryClubPreferences", "");
  m.setDescription("");
  m.addRequired("Club", "");
  access.getOutputDoc().addMethod(m);
}
if (true) {
  com.dexels.navajo.document.Method m = NavajoFactory.getInstance().createMethod(access.getOutputDoc(), "club/ProcessGetClubAccommodations", "");
  m.setDescription("");
  m.addRequired("Club", "");
  access.getOutputDoc().addMethod(m);
}
if (true) {
  com.dexels.navajo.document.Method m = NavajoFactory.getInstance().createMethod(access.getOutputDoc(), "club/ProcessGetDefaultClubFacility", "");
  m.setDescription("");
  m.addRequired("Club", "");
  access.getOutputDoc().addMethod(m);
}
if (true) {
  com.dexels.navajo.document.Method m = NavajoFactory.getInstance().createMethod(access.getOutputDoc(), "club/ProcessQueryClubReceipts", "");
  m.setDescription("");
  m.addRequired("Club", "");
  access.getOutputDoc().addMethod(m);
}
if (true) {
  com.dexels.navajo.document.Method m = NavajoFactory.getInstance().createMethod(access.getOutputDoc(), "competition/knvbnl/ProcessGetThisWeeksProgram", "");
  m.setDescription("");
  m.addRequired("Club", "");
  access.getOutputDoc().addMethod(m);
}
if (true) {
  com.dexels.navajo.document.Method m = NavajoFactory.getInstance().createMethod(access.getOutputDoc(), "competition/knvbnl/ProcessGetLastWeeksProgram", "");
  m.setDescription("");
  m.addRequired("Club", "");
  access.getOutputDoc().addMethod(m);
}
if (true) {
  com.dexels.navajo.document.Method m = NavajoFactory.getInstance().createMethod(access.getOutputDoc(), "facility/ProcessQueryClubFacilities", "");
  m.setDescription("");
  m.addRequired("Club", "");
  access.getOutputDoc().addMethod(m);
}
if (true) {
  com.dexels.navajo.document.Method m = NavajoFactory.getInstance().createMethod(access.getOutputDoc(), "team/ProcessQueryTeamBlackoutDates", "");
  m.setDescription("");
  m.addRequired("Club", "");
  access.getOutputDoc().addMethod(m);
}
if (true) {
  com.dexels.navajo.document.Method m = NavajoFactory.getInstance().createMethod(access.getOutputDoc(), "club/ProcessQueryClubContributions", "");
  m.setDescription("");
  m.addRequired("Club", "");
  access.getOutputDoc().addMethod(m);
}
if (true) {
  com.dexels.navajo.document.Method m = NavajoFactory.getInstance().createMethod(access.getOutputDoc(), "club/ProcessQueryOtherSports", "");
  m.setDescription("");
  m.addRequired("Club", "");
  access.getOutputDoc().addMethod(m);
}
if (true) {
  com.dexels.navajo.document.Method m = NavajoFactory.getInstance().createMethod(access.getOutputDoc(), "team/InitQueryTeams", "");
  m.setDescription("");
  m.addRequired("Club", "");
  access.getOutputDoc().addMethod(m);
}
if (true) {
  com.dexels.navajo.document.Method m = NavajoFactory.getInstance().createMethod(access.getOutputDoc(), "team/InitUpdateTeam", "");
  m.setDescription("");
  m.addRequired("Club", "");
  access.getOutputDoc().addMethod(m);
}
if (true) {
  com.dexels.navajo.document.Method m = NavajoFactory.getInstance().createMethod(access.getOutputDoc(), "club/ProcessQueryClubFunctions", "");
  m.setDescription("");
  m.addRequired("Club", "");
  access.getOutputDoc().addMethod(m);
}
if (true) {
  com.dexels.navajo.document.Method m = NavajoFactory.getInstance().createMethod(access.getOutputDoc(), "member/ProcessQueryAllMembersPerClub", "");
  m.setDescription("");
  m.addRequired("Club", "");
  access.getOutputDoc().addMethod(m);
}
if (true) {
  com.dexels.navajo.document.Method m = NavajoFactory.getInstance().createMethod(access.getOutputDoc(), "member/ProcessQueryAllPlayersPerClub", "");
  m.setDescription("");
  m.addRequired("Club", "");
  access.getOutputDoc().addMethod(m);
}
if (true) {
  com.dexels.navajo.document.Method m = NavajoFactory.getInstance().createMethod(access.getOutputDoc(), "discipline/license/ProcessQueryClubMilestones", "");
  m.setDescription("");
  m.addRequired("Club", "");
  access.getOutputDoc().addMethod(m);
}
if (true) {
  com.dexels.navajo.document.Method m = NavajoFactory.getInstance().createMethod(access.getOutputDoc(), "discipline/license/ProcessGetHistoricalPlans", "");
  m.setDescription("");
  m.addRequired("Club", "");
  access.getOutputDoc().addMethod(m);
}
if (true) {
  com.dexels.navajo.document.Method m = NavajoFactory.getInstance().createMethod(access.getOutputDoc(), "clubforms/ProcessGetNewForms", "");
  m.setDescription("");
  m.addRequired("Club", "");
  access.getOutputDoc().addMethod(m);
}
if (true) {
  com.dexels.navajo.document.Method m = NavajoFactory.getInstance().createMethod(access.getOutputDoc(), "competition/cif/ProcessGetPreventionMatrix", "");
  m.setDescription("");
  m.addRequired("Club", "");
  access.getOutputDoc().addMethod(m);
}
if (true) {
  com.dexels.navajo.document.Method m = NavajoFactory.getInstance().createMethod(access.getOutputDoc(), "vla/ProcessPrintClubOfficials", "");
  m.setDescription("");
  m.addRequired("Club", "");
  access.getOutputDoc().addMethod(m);
}
if (true) {
  com.dexels.navajo.document.Method m = NavajoFactory.getInstance().createMethod(access.getOutputDoc(), "vla/clubforms/InitListFiles", "");
  m.setDescription("");
  access.getOutputDoc().addMethod(m);
}
if (true) {
  com.dexels.navajo.document.Method m = NavajoFactory.getInstance().createMethod(access.getOutputDoc(), "club/ProcessGetClubFacilityStatistics", "");
  m.setDescription("");
  access.getOutputDoc().addMethod(m);
}
}// EOM
private final void execute_sub0(Access access) throws Exception {

  if (!kill) {
  count = 1;
  Message [] messageList0 = null;
  messageList0 = MappingUtils.addMessage(access.getOutputDoc(), currentOutMsg, "Club", "", count, "", "", "");
  for (int messageCount2 = 0; messageCount2 < messageList0.length; messageCount2++) {
 if (!kill) {
    outMsgStack.push(currentOutMsg);
    currentOutMsg = messageList0[messageCount2];
    access.setCurrentOutMessage(currentOutMsg);
    matchingConditions = false;
    if (Condition.evaluate("?[/@ClubId] AND Trim([/@ClubId]) != ''", access.getInDoc(), currentMap, currentInMsg, currentParamMsg))    {
      op = Expression.evaluate("[/@ClubId]", access.getInDoc(), currentMap, currentInMsg, currentParamMsg, currentSelection, null);
      sValue = op.value;
      matchingConditions = true;
    }
     else 
    {
      sValue = null;
      matchingConditions = true;
    }
    type = (sValue != null) ? MappingUtils.determineNavajoType(sValue) : "string";
    subtype = "";
    p = MappingUtils.setProperty(false, currentOutMsg, "ClubIdentifier", sValue, type, subtype, "in", "", 8, access.getOutputDoc(), access.getInDoc(), !matchingConditions);
p.setCardinality("1");
    matchingConditions = false;
    if (Condition.evaluate("?[/Club/LastName]", access.getInDoc(), currentMap, currentInMsg, currentParamMsg))    {
      op = Expression.evaluate("[/Club/LastName]", access.getInDoc(), currentMap, currentInMsg, currentParamMsg, currentSelection, null);
      sValue = op.value;
      matchingConditions = true;
    }
     else 
    {
      sValue = "-";
      matchingConditions = true;
    }
    type = (sValue != null) ? MappingUtils.determineNavajoType(sValue) : "string";
    subtype = "";
    p = MappingUtils.setProperty(false, currentOutMsg, "LastName", sValue, type, subtype, "in", "", 8, access.getOutputDoc(), access.getInDoc(), !matchingConditions);
p.setCardinality("1");
  currentOutMsg = (Message) outMsgStack.pop();
  access.setCurrentOutMessage(currentOutMsg);
  }
 } // EOF messageList for 
  }
}


public String getAuthor() {
   return "Arjen Schoneveld";
}

public String getDescription() {
   return "Initialization script for maintenance of Clubs";
}

public String getScriptType() {
   return "tsl";
}

}//EOF