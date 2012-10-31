/**
 * Created with IntelliJ IDEA.
 * User: Malcolm
 * Date: 10/28/12
 * Time: 1:20 PM
 * To change this template use File | Settings | File Templates.
 */
Ext.define("TabRunner.views.NewTeamForm", {
    extend: "Ext.form.Panel",
    alias: "widget.newTeamForm",

    padding: 10,
    defaultType: "textfield",
    items: [
        {
            fieldLabel: "Team Number",
            name: "teamNumber",
            allowBlank: false

        },
        {
            fieldLabel: "School Name",
            name: "schoolName",
            allowBlank: false
        },
        {
            fieldLabel: "Coach Name",
            name: "coachName",
            allowBlank: false
        },
        {
            xtype: "grid",
            height: 225,
            store: Ext.create("Ext.data.Store", {
                storeId: "newCompetitorStore",
                autoLoad: true,
                fields: ["competitorName"]
            }),
            columns: [
                {header: "Name", dataIndex: "competitorName", resizable: false, flex: 1}
            ],
            tbar: [
                {
                    xtype: "button",
                    text: "New Competitor",
                    handler: function(button) {
                        Ext.Msg.prompt("New Tournament", "Input the name of the competitor",
                            function(buton, response, messageBox) {
                                var newCompetitorStore = Ext.StoreManager.get("newCompetitorStore");
                                newCompetitorStore.add({competitorName:response});
                            }
                        );
                    }
                }
            ]
        }
    ]
});