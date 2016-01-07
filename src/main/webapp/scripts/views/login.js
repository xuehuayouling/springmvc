//<debug>
//Ext.Loader.setPath({
//	'Ext' : '../../src'
//});
// </debug>

/**
 * This example is a simple demo of some of the form and field components in
 * Sencha Touch. It also shows how you can bind a Model instance to a form, and
 * then update that instance with values from the form panel.
 */

/**
 * Here we define a User model. An instance of this model will be used to load
 * data into our form panel. We will also update this form panel when you press
 * the submit button.
 */
Ext.define('User', {
	extend : 'Ext.data.Model',

	config : {
		fields : [ {
			name : 'name',
			type : 'string'
		}, {
			name : 'password',
			type : 'string'
		} ]
	}
});

// Define our simple application
Ext.application({

	isIconPrecomposed : false,

	// Require the components we will be using in this example
	requires : [ 'Ext.form.*', 'Ext.field.*', 'Ext.Button', 'Ext.Toolbar',

	'Ext.data.Store', 'Ext.MessageBox', 'Ext.JSON' ],

	/**
	 * The launch method of our application gets called when the application is
	 * good to launch. In here, we are going to build the structure of our
	 * application and add it into the Viewport.
	 */
	launch : function() {
		// Get all the items for our form.
		var items = this.getFormItems(), config, form;

		// Create the configuration of our form.
		// We give it the `formpanel` xtype and give it the items we got above.
		config = {
			xtype : 'formpanel',
			layout: {
			    type: 'vbox',
			    align: 'middle ',
			    pack: 'center'
			},
			items : items
		};

		// If we are on a phone, we just want to add the form into the viewport
		// as is.
		// This will make it stretch to the size of the Viewport.
		if (Ext.os.deviceType == 'Phone') {
			form = Ext.Viewport.add(config);
		} else {
			// If we are not on a phone, we want to make the formpanel model and
			// give it a fixed with and height.
			Ext.apply(config, {
				modal : true,
				height : '90%',
				width : '60%',
				centered : true,

				// Disable hideOnMaskTap so it cannot be hidden
				hideOnMaskTap : false
			});

			// Add it to the Viewport and show it.
			form = Ext.Viewport.add(config);
			form.show();
		}

		this.form = form;
	},

	/**
	 * This method returns an array of all items we should add into the form
	 * panel we create above in our launch function. We have created this
	 * function to simply make things cleaner and easier to read and understand.
	 * You could just put these items inline up above in the form `config`.
	 * 
	 * @return {Array} items
	 */
	getFormItems : function() {
		return [ {
			xtype : 'fieldset',
			instructions : 'Please enter the information above.',
			defaults : {
				required : true
			},
			items : [ {
				xtype : 'textfield',
				name : 'name',
				label : 'Name',
				autoCapitalize : false
			}, {
				xtype : 'passwordfield',
				name : 'password',
				label : 'Password'
			} ]
		}, {
				xtype : 'button',
				text : 'Submit',
				ui: 'confirm',
                scope: this,
				handler : function() {
					var form = this.form;
					form.submit({
						url : 'login',
						waitMsg : 'Logining...',
						success : function(form1, result1, data1) {
							var dto = Ext.JSON.decode(data1);
							if (dto.success == 1) {
								window.location.href = '../access/test?token=' + dto.result
							} else {
								Ext.Msg.alert('', dto.result);
							}
						},
						failure : function(form2, result2) {
							Ext.Msg.alert('', JSON.stringify(result2));
						}
					});
				}
		} ];
	}
});
