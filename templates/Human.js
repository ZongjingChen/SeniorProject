import {loader, clock, scene, camera, renderer, THREE} from "./templateFbxLoader.js"

const MODEL_PATH = '../res/models/Samba Dancing.fbx';

class Human {

    constructor(){
        loader.load(MODEL_PATH, function (object) {
                // load object and add to scene
                let mixer = new THREE.AnimationMixer(object);

                // parse model
                const action = mixer.clipAction( object.animations[1] );
                action.play();
                scene.add( object );
                playAnimation(object);
            }
        );

//         loader.load(MODEL_PATH, new Human(object).act());

        //TODO: find each body part of the model
        // this.leftArm =
    }

    playAnimation(object) {
        console.log(object);
        animate();

        function animate() {
            requestAnimationFrame(animate);

            const delta = clock.getDelta();
            if (mixer) mixer.update(delta);
            let skeleton = object.children[0].children[2];
            let degree = 0.6;
            if (skeleton.rotation.x < degree) {
                riseLeg(degree);
            } else {
                if (object.children[0].rotation.x < 0.7) {
                    object.children[0].rotation.x += 0.01 / 2;
                    object.children[0].children[3].rotation.x -= 0.01 / 2;
                    object.children[2].skeleton.bones[26].rotation.y += 0.018 / 2;
                    object.children[2].skeleton.bones[27].rotation.y += 0.022 / 2;
                    object.children[2].skeleton.bones[28].rotation.y += 0.006 / 2;
                }
            }

            function riseLeg(degree) {
                if (skeleton.rotation.x < degree) {
                    skeleton.rotation.x += 0.01 / 2;
                }
            }

            renderer.render(scene, camera);
        }
    }

    act(){

    }
}



export {Human};