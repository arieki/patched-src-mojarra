<!--
 Copyright 2003 Sun Microsystems, Inc. All rights reserved.
 SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
-->

           <tr>

             <td>

                 <h:outputText id="shipmentLabel" 
                     value="Radio with hard-coded options laid out horizontally" />
             </td>


             <td>
                 <h:selectOneRadio id="shipType" layout="LINE_DIRECTION" 
                    value="nextDay" tabindex="30" title="shipType">
                <f:selectItem itemValue="nextDay" itemLabel="Next Day"/>
                <f:selectItem itemValue="nextWeek" itemLabel="Next Week" />
                <f:selectItem itemValue="nextMonth" itemLabel="Next Month"/>
                </h:selectOneRadio>

             </td>

            </tr>

            <tr>

             <td>
                 <h:outputText id="verticalLabel" 
                     value="Radio with hard-coded options laid out vertically" />

             </td>


             <td>
                <h:selectOneRadio id="verticalRadio" layout="pageDirection" border="1" >
                  <f:selectItem itemValue="nextDay" itemLabel="Next Day"/>
                  <f:selectItem itemValue="nextWeek" itemLabel="Next Week"  />
                  <f:selectItem itemValue="nextMonth" itemLabel="Next Month" />

                </h:selectOneRadio>

             </td>

            </tr>

             <tr>

             <td>
                 <h:outputText id="modelLabel" value="Radio with options from model " />

             </td>


             <td>
                 <h:selectOneRadio id="radioFromModel"
                       value="#{LoginBean.currentOption}"
                       title="options come from model"
                       layout="LINE_DIRECTION" >

                 <f:selectItems id="radioOptions" value="#{LoginBean.options}"/>
                </h:selectOneRadio>

             </td>

            </tr>

             <tr>

             <td>
                 <h:outputText id="modelLongLabel" value="Radio with options 
                     from model of type java.lang.Long" />

             </td>


             <td>
                 <h:selectOneRadio id="radioLongOptions"
                       value="#{LoginBean.currentLongOption}"
                       title="options come from model"
                       layout="LINE_DIRECTION">
                 
                <f:selectItems id="longItemOptions" value="#{LoginBean.longList}"/>
                </h:selectOneRadio>

             </td>

            </tr>

            <tr>
	         <td>Single-select radiomodel - modelType Boolean:</td>
	         <td><h:selectOneRadio id="oneLongradiomodel"
                            value="#{LoginBean.currentBooleanOption}">
                           
		         <f:selectItems id="oneRadiomodelitems"
				value="#{LoginBean.booleanList}" />
		    </h:selectOneRadio></td>
	   </tr>

           <tr>

             <td>
                 <h:outputText value="Radio with optionGroups from model " />

             </td>


             <td>
                 <h:selectOneRadio id="radioFromModelGroup"
                       value="#{LoginBean.currentOption}"
                       title="options come from model"
                       layout="LINE_DIRECTION" >

                 <f:selectItems id="radioOptionsGroup" value="#{LoginBean.optionsGroup}"/>
                </h:selectOneRadio>

             </td>

            </tr>



