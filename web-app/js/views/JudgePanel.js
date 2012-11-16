/**
 * Created with IntelliJ IDEA.
 * User: Malcolm
 * Date: 10/19/12
 * Time: 11:18 AM
 * To change this template use File | Settings | File Templates.
 */
Ext.define("TabRunner.views.JudgePanel", {
    extend: "Ext.grid.Panel",
    alias: "widget.judgePanel",
    store: Ext.create("Ext.data.Store", {
        storeId: "judgeStore",
        fields: ["judgeName"],
        proxy: {
            type: "ajax"
        }
    }),
    columns: [
        {header: "Judge Name", dataIndex: "judgeName"}
    ],
    tbar: [
        {
            xtype: "button",
            id: "addJudgeButton",
            text: "New",
            disabled: true,
            handler: function(button) {
                var currentTournamentId = Ext.getCmp("tournamentList").getSelected().get("id");
                Ext.Msg.prompt("New Judge", "Input the name of the judge",
                    function(buton, response, messageBox) {
                        Ext.Ajax.request({
                            url: "/TabRunner/Tournament/addJudge/" + currentTournamentId,
                            params: {
                                judgeName:response
                            },
                            success: function() {
                                Ext.StoreManager.get("judgeStore").load({
                                    url: "/TabRunner/Tournament/judges/" + currentTournamentId
                                });
                            }
                        });
                    }
                );
            }
        }
    ]
});