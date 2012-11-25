/**
 * Created with IntelliJ IDEA.
 * User: Malcolm
 * Date: 11/25/12
 * Time: 2:31 PM
 * To change this template use File | Settings | File Templates.
 */
Ext.define("TabRunner.views.Ballot", {
    extend: "Ext.form.Panel",
    alias: "widget.ballot",

    items: [
        {
            xtype: "textfield",
            name: "judge",
            fieldLabel: "Judge",
            dataIndex: "judge"
        },
        {
            xtype: "textfield",
            name: "teamP",
            fieldLabel: "Plaintiff/Prosecution",
            dataIndex: "teamP"
        },
        {
            xtype: "textfield",
            name: "teamD",
            fieldLabel: "Defense",
            dataIndex: "teamD"
        },
        {
            xtype: "textfield",
            name: "pOpening",
            fieldLabel: "P Opening",
            dataIndex: "pOpening"
        }
    ]

});