/**
 * Created with IntelliJ IDEA.
 * User: Malcolm
 * Date: 10/19/12
 * Time: 11:19 AM
 * To change this template use File | Settings | File Templates.
 */
Ext.define("TabRunner.views.RoundPanel", {
    extend: "Ext.grid.Panel",
    alias: "widget.roundPanel",
    store: Ext.create("Ext.data.Store", {
        storeId: "roundStore",
        autoLoad: true,
        fields: ["roundName"],
        data: [
            {"roundName": "Round 1"},
            {"roundName": "Round 2"}
        ]
    }),
    columns: [
        {header: "Round Name", dataIndex: "roundName"}
    ]
});