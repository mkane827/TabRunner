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
        autoLoad: true,
        fields: ["judgeName"],
        data: [
            {"judgeName": "Judge 1"},
            {"judgeName": "Judge 2"}
        ]
    }),
    columns: [
        {header: "Judge Name", dataIndex: "judgeName"}
    ]
});