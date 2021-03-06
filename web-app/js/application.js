if (typeof jQuery !== 'undefined') {
	(function($) {
		$('#spinner').ajaxStart(function() {
			$(this).fadeIn();
		}).ajaxStop(function() {
			$(this).fadeOut();
		});
	})(jQuery);
}
Ext.Loader.setConfig({enabled:true});
Ext.application({
    name: "TabRunner",
    appFolder:"/TabRunner/js",
    requires: [
        "TabRunner.views.TournamentList",
        "TabRunner.views.TeamPanel",
        "TabRunner.views.JudgePanel",
        "TabRunner.views.RoundPanel",
        "TabRunner.views.TeamRankings",
        "TabRunner.views.IndividualRankings",
        "TabRunner.views.NewTeamForm",
        "TabRunner.views.EditTeamForm",
        "TabRunner.views.RoundInfoTab",
        "TabRunner.views.Ballot"
    ],
    launch: function() {
        Ext.create('Ext.container.Viewport', {
            layout: "fit",
            items: [
                {
                    xtype: 'panel',
                    layout: 'border',
                    items: [
                        {
                            region: "north",
                            title: "TabRunner",
                            html: "Hello! Welcome to Malcolm's Capstone Project: TABRUNNER!"
                        },
                        {
                            xtype:'tabpanel',
                            region: 'center',
                            items: [
                                {
                                    xtype: "teamPanel",
                                    title: "Teams"
                                },
                                {
                                    xtype: "judgePanel",
                                    title: "Judges"
                                },
                                {
                                    xtype: "roundPanel",
                                    title: "Rounds"
                                },
                                {
                                    xtype: "teamRankings",
                                    title: "Team Rankings"
                                },
                                ,
                                {
                                    xtype: "individualRankings",
                                    title: "Individual Rankings"
                                }
                            ]
                        },
                        {
                            region: "west",
                            xtype: "tournamentList",
                            title: "Tournaments",
                            id: "tournamentList"
                        }
                    ]
                }
            ]
        });
    }
});