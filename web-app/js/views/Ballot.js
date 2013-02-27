/**
 * Created with IntelliJ IDEA.
 * User: Malcolm
 * Date: 11/25/12
 * Time: 2:31 PM
 * To change this template use File | Settings | File Templates.
 */
Ext.define("BallotModel", {
    extend: "Ext.data.Model",
    fields: ["judge", "round", "teamP", "teamD", "pOpening", "dOpening",
        "pDirectAttorney1", "pDirectAttorney2", "pDirectAttorney3",
        "pDirectWitness1", "pDirectWitness2", "pDirectWitness3",
        "pCrossWitness1", "pCrossWitness2", "pCrossWitness3",
        "pCrossAttorney1", "pCrossAttorney2", "pCrossAttorney3",
        "dDirectAttorney1", "dDirectAttorney2", "dDirectAttorney3",
        "dDirectWitness1", "dDirectWitness2", "dDirectWitness3",
        "dCrossWitness1", "dCrossWitness2", "dCrossWitness3",
        "dCrossAttorney1", "dCrossAttorney2", "dCrossAttorney3",
        "pClosing", "dClosing"
         ]
});

Ext.define("TabRunner.views.Ballot", {
    extend: "Ext.form.Panel",
    alias: "widget.ballot",
    layout: "border",
    items: [
        {
            layout: "hbox",
            region: "north",
            defaults: {
                labelWidth: 40
            },
            items: [
                {
                    xtype: "displayfield",
                    name: "judge",
                    fieldLabel: "Judge",
                    dataIndex: "judge",
                    padding: "0 30 0 30"
                },
                {
                    xtype: "displayfield",
                    name: "round",
                    fieldLabel: "Round",
                    dataIndex: "round"
                }
            ]
        },
        {
            layout: "column",
            region: 'center',
            autoScroll: true,
            items: [
                {
                    columnWidth:.5,
                    defaults: {
                        labelWidth: 120,
                        xtype: "numberfield",
                        minValue: 1,
                        maxValue: 10
                    },
                    items: [
                        {
                            xtype: "displayfield",
                            name: "teamP",
                            fieldLabel: "Plaintiff/Prosecution",
                            dataIndex: "teamP"
                        },
                        {
                            name: "pOpening",
                            fieldLabel: "P Opening",
                            dataIndex: "pOpening"
                        },
                        {
                            name: "pDirectAttorney1",
                            fieldLabel: "P Direct Attorney 1",
                            dataIndex: "pDirectAttorney1"
                        },
                        {
                            name: "pDirectWitness1",
                            fieldLabel: "P Direct Witness 1",
                            dataIndex: "pDirectWitness1"
                        },
                        {
                            name: "pCrossWitness1",
                            fieldLabel: "P Cross Witness 1",
                            dataIndex: "pCrossWitness1"
                        },
                        {
                            name: "pDirectAttorney2",
                            fieldLabel: "P Direct Attorney 2",
                            dataIndex: "pDirectAttorney2"
                        },
                        {
                            name: "pDirectWitness2",
                            fieldLabel: "P Direct Witness 2",
                            dataIndex: "pDirectWitness2"
                        },
                        {
                            name: "pCrossWitness2",
                            fieldLabel: "P Cross Witness 2",
                            dataIndex: "pCrossWitness2"
                        },
                        {
                            name: "pDirectAttorney3",
                            fieldLabel: "P Direct Attorney 3",
                            dataIndex: "pDirectAttorney3"
                        },
                        {
                            name: "pDirectWitness3",
                            fieldLabel: "P Direct Witness 3",
                            dataIndex: "pDirectWitness3"
                        },
                        {
                            name: "pCrossWitness3",
                            fieldLabel: "P Cross Witness 3",
                            dataIndex: "pCrossWitness3"
                        },
                        {
                            name: "pCrossAttorney1",
                            fieldLabel: "P Cross Attorney 1",
                            dataIndex: "pCrossAttorney1"
                        },
                        {
                            name: "pCrossAttorney2",
                            fieldLabel: "P Cross Attorney 2",
                            dataIndex: "pCrossAttorney2"
                        },
                        {
                            name: "pCrossAttorney3",
                            fieldLabel: "P Cross Attorney 3",
                            dataIndex: "pCrossAttorney3"
                        },
                        {
                            name: "pClosing",
                            fieldLabel: "P Closing",
                            dataIndex: "pClosing"
                        }
                    ]
                },
                {
                    columnWidth:.5,
                    defaults: {
                        labelWidth: 120,
                        xtype: "numberfield",
                        minValue: 1,
                        maxValue: 10
                    },
                    items: [
                        {
                            xtype: "displayfield",
                            name: "teamD",
                            fieldLabel: "Defense",
                            dataIndex: "teamD"
                        },
                        {
                            name: "dOpening",
                            fieldLabel: "D Opening",
                            dataIndex: "dOpening"
                        },
                        {
                            name: "dCrossAttorney1",
                            fieldLabel: "D Cross Attorney 1",
                            dataIndex: "dCrossAttorney1"
                        },,
                        {
                            name: "dCrossAttorney2",
                            fieldLabel: "D Cross Attorney 2",
                            dataIndex: "dCrossAttorney2"
                        },
                        {
                            name: "dCrossAttorney3",
                            fieldLabel: "D Cross Attorney 3",
                            dataIndex: "dCrossAttorney3"
                        },
                        {
                            name: "dDirectAttorney1",
                            fieldLabel: "D Direct Attorney 1",
                            dataIndex: "dDirectAttorney1"
                        },
                        {
                            name: "dDirectWitness1",
                            fieldLabel: "D Direct Witness 1",
                            dataIndex: "dDirectWitness1"
                        },
                        {
                            name: "dCrossWitness1",
                            fieldLabel: "D Cross Witness 1",
                            dataIndex: "dCrossWitness1"
                        },
                        {
                            name: "dDirectAttorney2",
                            fieldLabel: "D Direct Attorney 2",
                            dataIndex: "dDirectAttorney2"
                        },
                        {
                            name: "dDirectWitness2",
                            fieldLabel: "D Direct Witness 2",
                            dataIndex: "dDirectWitness2"
                        },
                        {
                            name: "dCrossWitness2",
                            fieldLabel: "D Cross Witness 2",
                            dataIndex: "dCrossWitness2"
                        },
                        {
                            name: "dDirectAttorney3",
                            fieldLabel: "D Direct Attorney 3",
                            dataIndex: "dDirectAttorney3"
                        },
                        {
                            name: "dDirectWitness3",
                            fieldLabel: "D Direct Witness 3",
                            dataIndex: "dDirectWitness3"
                        },
                        {
                            name: "dCrossWitness3",
                            fieldLabel: "D Cross Witness 3",
                            dataIndex: "dCrossWitness3"
                        },
                        {
                            name: "dClosing",
                            fieldLabel: "D Closing",
                            dataIndex: "dClosing"
                        }
                     ]
                }
            ]
        },
        {
            layout: "column",
            region: "south",
            items: [
                {
                    columnWidth:.5,
                    defaults: {
                        labelWidth: 120,
                        xtype: "combo",
                        store: Ext.StoreManager.get("competitorStore"),
                        queryMode: "local",
                        displayField: "competitorName"
                    },
                    items: [
                        {
                            fieldLabel: "Rank 1 Attorney"
                        },
                        {
                            fieldLabel: "Rank 2 Attorney"
                        },
                        {
                            fieldLabel: "Rank 3 Attorney"
                        },
                        {
                            fieldLabel: "Rank 4 Attorney"
                        },
                    ]
                },
                {
                    columnWidth:.5,
                    defaults: {
                        labelWidth: 120,
                        xtype: "combo",
                        store: Ext.StoreManager.get("competitorStore"),
                        queryMode: "local",
                        displayField: "competitorName"
                    },
                    items: [
                        {
                            fieldLabel: "Rank 1 Witness"
                        },
                        {
                            fieldLabel: "Rank 2 Witness"
                        },
                        {
                            fieldLabel: "Rank 3 Witness"
                        },
                        {
                            fieldLabel: "Rank 4 Witness"
                        },
                    ]
                }
            ]
        }
    ]

});