package code.lib

import net.liftweb.common.LazyLoggable
import net.liftweb.http.LiftRules

/**
 * Heirko project
 * User: Alexandre
 * Date: 07/06/12
 * Time: 20:43
 */

trait BaseService extends LazyLoggable {

  private var _state = false

  /**
   * Service state, true if active
   */
  def state() = _state

    final def init(): Unit = {
    internalInit()
    LiftRules.unloadHooks.append(
     () => stop())
    _state = true
    logger.info("Launched")
    }

  private final def stop() : Unit = {
    if (!_state) return
    InternalStop()
    _state = false
    logger.info("Stopped")
  }

  protected def InternalStop() : Unit

  protected def internalInit() : Unit
}