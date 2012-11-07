/**
 * Created with IntelliJ IDEA.
 * User: Malcolm
 * Date: 11/7/12
 * Time: 10:20 AM
 * To change this template use File | Settings | File Templates.
 */
Ext.define("TabRunner.views.EditTeamForm", {
    extend: "Ext.form.Panel",
    alias: "widget.editTeamForm",

    padding: 10,
    defaultType: "textfield",
    items: [
        {
            fieldLabel: "Team Number",
            name: "teamNumber",
            allowBlank: false,
            dataIndex: "teamNumber"

        },
        {
            fieldLabel: "School Name",
            name: "schoolName",
            allowBlank: false,
            dataIndex: "schoolName"
        },
        {
            fieldLabel: "Coach Name",
            name: "coachName",
            allowBlank: false,
            dataIndex: "coachName"
        },
        {
            xtype: "grid",
            height: 225,
            store: Ext.create("Ext.data.Store", {
                storeId: "editCompetitorStore",
                fields: ["competitorName"],
                proxy: {
                    type: "ajax"
                }
            }),
            columns: [
                {header: "Name", dataIndex: "competitorName", resizable: false, flex: 1, field: {xtype:"textfield"}}
            ],
            plugins: [
                Ext.create('Ext.grid.plugin.CellEditing', {
                    clicksToEdit: 2
                })
            ],
            tbar: [
                {
                    xtype: "button",
                    text: "New Competitor",
                    handler: function(button) {
                        Ext.Msg.prompt("New Tournament", "Input the name of the competitor",
                            function(buton, response, messageBox) {
                                var editCompetitorStore = Ext.StoreManager.get("editCompetitorStore");
                                editCompetitorStore.add({competitorName:response});
                            }
                        );
                    }
                }
            ]
        }
    ]
});