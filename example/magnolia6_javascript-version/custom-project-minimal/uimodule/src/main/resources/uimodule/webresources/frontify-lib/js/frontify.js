(function myFunction() {
    var container = document.getElementById('FinderWrapper');
    FrontifyFinder.open({
        container: container,
        success: assets => {
        for (var asset in assets) {
        // get the iframe by class
        let iframeContainer = window.document.getElementsByClassName("popupStyleNameForFrontifyLibrary")[0];
        let iframe = iframeContainer.getElementsByClassName("field-iframe")[0];
        var innerDoc = iframe.contentDocument || iframe.contentWindow.document;
        // get the id by class
        let input = innerDoc.getElementById("input");
        let imgInput = innerDoc.getElementById("imgInput");
        input.value = assets[asset].generic_url;
        imgInput.src = assets[asset].generic_url;
        input.dispatchEvent(new Event('change'))
    }
},
    cancel: () => {
        window.openObj("Selection cancelled!");
        let popup = window.parent.document.getElementsByClassName("popupStyleNameForFrontifyLibrary")[0];
        // get the id by class
        let popupId = popup.getAttribute("id");
        window.top.document.getElementById(popupId).dispatchEvent(new Event('change'));
    },
    error: message => {
        if (errorMessage.code === 'ERR_FINDER_MESSAGE') {
            localStorage.clear();
        }
        console.log(`Error fronty: ${message}`);
    },
    warning: message => {
        console.log(`Warning: ${message}`);
    },
    settings: {
        multiSelect: false,
            filters: [{
            key: 'object_type',
            values: ['IMAGE'],
            inverted: false,
        },
            {
                key: 'ext',
                values: ['eps', 'tif', 'ai'],
                inverted: true,
            }
        ],
            popup: {
            title: 'My Company\'s Frontify Assets',
                size: {
                width: 600,
                    height: 400,
            },
            position: {
                x: 50,
                    y: 50,
            },
        },
    }
});
}());