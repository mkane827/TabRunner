/**
 * Created with IntelliJ IDEA.
 * User: Malcolm
 * Date: 11/7/12
 * Time: 5:58 PM
 * To change this template use File | Settings | File Templates.
 */
Ext.define("TabRunner.views.RoundInfoTab", {
    extend: "Ext.grid.Panel",
    alias: "widget.roundInfoTab",

    closable: true,
    columns: [
        {header: "Plaintiff/Prosecution", dataIndex: "teamP"},
        {header: "Defense", dataIndex: "teamD"},
        {header: "Judge 1", dataIndex: "judge1"}
    ],
    listeners: {
        itemdblclick: function(grid, record, item, index, e, eOpts) {

        }
    }
});